from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import pandas as pd
import uvicorn
import os
import numpy as np
from sqlalchemy import (
    create_engine, select, func, Table, MetaData, Column,
    Integer, Float, ForeignKey, text, event
)
from sqlalchemy.exc import SQLAlchemyError
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from pathlib import Path

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Configuração do banco
def get_engine():
    try:
        DB_ENGINE = os.getenv("DB_ENGINE", "sqlite")
        DB_USER = os.getenv("DB_USER", "")
        DB_PASSWORD = os.getenv("DB_PASSWORD", "")
        DB_HOST = os.getenv("DB_HOST", "")
        DB_PORT = os.getenv("DB_PORT", "")
        DB_NAME = os.getenv("DB_NAME", "fallback.db")  # Padrão para testes off SQLite

        if DB_ENGINE == "sqlite":
            sqlite_file = DB_NAME if DB_NAME.endswith(".db") else f"{DB_NAME}.db"
            engine = create_engine(f"sqlite:///{sqlite_file}")
            
            @event.listens_for(engine, 'connect')
            def _enable_foreign_keys(dbapi_conn, connection_record):
                dbapi_conn.execute("PRAGMA foreign_keys=ON")
            
            return engine

        # Formata a conexão para qualquer banco
        url = f"{DB_ENGINE}://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"
        engine = create_engine(url)
        engine.connect()
        return engine

    except SQLAlchemyError as e:
        print(f" Erro ao conectar com o banco principal: {e}")
        try:
            print("🔁 Usando SQLite como fallback.")
            engine = create_engine("sqlite:///fallback.db")

            @event.listens_for(engine, 'connect')
            def _enable_fk_fallback(dbapi_conn, connection_record):
                dbapi_conn.execute("PRAGMA foreign_keys=ON")
            
            return engine
        except Exception as e:
            raise RuntimeError("Falha ao conectar com o banco de dados.") from e


engine = get_engine()


metadata = MetaData()

apresentacoes = Table('apresentacoes', metadata,
    Column('id', Integer, primary_key=True),
    Column('inscritos', Integer)
)

votos = Table('votos', metadata,
    Column('id', Integer, primary_key=True),
    Column('apresentacao_id', Integer, ForeignKey("apresentacoes.id")),
    Column('nota_geral', Float)
)

@app.get("/debug")
def debug():
    with engine.connect() as conn:
        query = select(votos)
        result = conn.execute(query)
        return [dict(row._mapping) for row in result]

@app.get("/ranking")
def gerar_ranking():
    try:
        with engine.connect() as conn:
            query = (
                select(
                    apresentacoes.c.id.label("apresentacao_id"),
                    apresentacoes.c.inscritos,
                    func.count(votos.c.id).label("votantes"),
                    func.coalesce(func.avg(votos.c.nota_geral), 0).label("media_estrelas")
                )
                .select_from(apresentacoes.outerjoin(votos))
                .group_by(apresentacoes.c.id)
            )
            
            df = pd.read_sql(query, conn)

        df['taxa_participacao'] = np.where(
            df['inscritos'] > 0,
            df['votantes'] / df['inscritos'],
            0
        )
        
        df["posicao"] = df["media_estrelas"].rank(
            method="dense",
            ascending=False
        ).astype(int)

        if len(df) >= 3:
            scaler = StandardScaler()
            X = scaler.fit_transform(df[['media_estrelas', 'taxa_participacao']])
            
            kmeans = KMeans(n_clusters=3, random_state=42, n_init='auto')
            df['grupo'] = kmeans.fit_predict(X)
            
            centroides = scaler.inverse_transform(kmeans.cluster_centers_)
            ordem = centroides.mean(axis=1).argsort()
            df['aceitacao'] = df['grupo'].map({
                ordem[0]: "baixa aceitação",
                ordem[1]: "média aceitação",
                ordem[2]: "alta aceitação"
            })
        else:
            df['aceitacao'] = "média aceitação"

        return df.sort_values("posicao").to_dict(orient="records")

    except Exception as e:
        print(f"Erro: {e}")
        return []

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
