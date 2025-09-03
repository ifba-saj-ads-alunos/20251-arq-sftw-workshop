from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
import pandas as pd
import uvicorn
import os
import numpy as np
from sqlalchemy import (
    create_engine, select, func, Table, MetaData, Column,
    Integer, Float, String, ForeignKey, event
)
from sqlalchemy.exc import SQLAlchemyError
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from collections import Counter
import re

app = FastAPI(title="API de Ranking de Apresentações")

# Permitir requisições de qualquer origem (React front-end)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# =============================
# 1. FUNÇÃO PARA CONEXÃO COM O BANCO
# =============================
def get_engine():
    try:
        DB_ENGINE = os.getenv("DB_ENGINE", "sqlite")
        DB_USER = os.getenv("DB_USER", "")
        DB_PASSWORD = os.getenv("DB_PASSWORD", "")
        DB_HOST = os.getenv("DB_HOST", "")
        DB_PORT = os.getenv("DB_PORT", "")
        DB_NAME = os.getenv("DB_NAME", "fallback.db")

        if DB_ENGINE == "sqlite":
            sqlite_file = DB_NAME if DB_NAME.endswith(".db") else f"{DB_NAME}.db"
            engine = create_engine(f"sqlite:///{sqlite_file}")
            @event.listens_for(engine, 'connect')
            def _enable_foreign_keys(dbapi_conn, connection_record):
                dbapi_conn.execute("PRAGMA foreign_keys=ON")
            return engine

        url = f"{DB_ENGINE}://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"
        engine = create_engine(url)
        engine.connect()
        return engine

    except SQLAlchemyError as e:
        print(f"Erro ao conectar com o banco principal: {e}")
        print("Usando SQLite como fallback.")
        return create_engine("sqlite:///fallback.db")

engine = get_engine()
metadata = MetaData()

# =============================
# 2. DEFINIÇÃO DAS TABELAS
# =============================
apresentacoes = Table('apresentacoes', metadata,
    Column('id', Integer, primary_key=True),
    Column('titulo', String),
    Column('inscritos', Integer)
)

votos = Table('votos', metadata,
    Column('id', Integer, primary_key=True),
    Column('apresentacao_id', Integer, ForeignKey("apresentacoes.id")),
    Column('nota_geral', Float)
)

# =============================
# 3. FUNÇÃO DE EXTRAÇÃO DE PALAVRAS-CHAVE
# =============================
def extrair_palavras_chave(texto, top_n=5):
    if not texto:
        return []
    palavras = re.findall(r'\b\w+\b', texto.lower())
    stopwords = {'de', 'do', 'da', 'em', 'para', 'com', 'e', 'a', 'o', 'um', 'uma'}
    palavras_filtradas = [p for p in palavras if p not in stopwords]
    return [p[0] for p in Counter(palavras_filtradas).most_common(top_n)]

# =============================
# 4. TREINAMENTO DO MODELO PREDITIVO
# =============================
def treinar_modelo(df):
    if len(df) < 5:
        return None
    X = df[['media_estrelas', 'taxa_participacao', 'inscritos']].fillna(0)
    X.columns = [str(col) for col in X.columns]
    X = X.astype(float)
    y = (df['media_estrelas'] >= 4.0).astype(int)
    modelo = RandomForestClassifier(random_state=42)
    modelo.fit(X, y)
    return modelo

# =============================
# 5. FUNÇÃO DE GERAÇÃO DE RANKING
# =============================
def gerar_df_ranking(min_media=0, min_taxa=0, filtro_aceitacao=None):
    with engine.connect() as conn:
        query = (
            select(
                apresentacoes.c.titulo.label("nome"),
                apresentacoes.c.inscritos,
                func.count(votos.c.id).label("votantes"),
                func.coalesce(func.avg(votos.c.nota_geral), 0).label("media_estrelas")
            )
            .select_from(apresentacoes.outerjoin(votos))
            .group_by(apresentacoes.c.id)
        )
        df = pd.read_sql(query, conn)

    # Corrigir nomes de colunas
    df.columns = [str(col) for col in df.columns]

    df['taxa_participacao'] = np.where(df['inscritos'] > 0, df['votantes'] / df['inscritos'], 0)
    df["posicao"] = df["media_estrelas"].rank(method="dense", ascending=False).astype(int)

    # Aplicar filtros
    df = df[df['media_estrelas'] >= min_media]
    df = df[df['taxa_participacao'] >= min_taxa]

    # Cluster para classificação de aceitação
    if len(df) >= 3:
        scaler = StandardScaler()
        X_cluster = scaler.fit_transform(df[['media_estrelas', 'taxa_participacao']])
        kmeans = KMeans(n_clusters=3, random_state=42, n_init='auto')
        df['grupo'] = kmeans.fit_predict(X_cluster)

        centroides = scaler.inverse_transform(kmeans.cluster_centers_)
        ordem = centroides.mean(axis=1).argsort()
        df['aceitacao'] = df['grupo'].map({
            ordem[0]: "baixa aceitação",
            ordem[1]: "média aceitação",
            ordem[2]: "alta aceitação"
        })
    else:
        df['aceitacao'] = "média aceitação"

    if filtro_aceitacao:
        df = df[df['aceitacao'] == filtro_aceitacao]

    # Palavras-chave
    df["palavras_chave"] = df["nome"].apply(lambda x: extrair_palavras_chave(x))

    # Predição
    modelo = treinar_modelo(df)
    if modelo is not None:
        X_pred = df[['media_estrelas', 'taxa_participacao', 'inscritos']].fillna(0)
        X_pred.columns = [str(col) for col in X_pred.columns]
        X_pred = X_pred.astype(float)
        df["predito_bem_aceito"] = modelo.predict(X_pred)

    return df.sort_values("posicao")

# =============================
# 6. ENDPOINTS
# =============================
@app.get("/debug")
def debug():
    with engine.connect() as conn:
        result = conn.execute(select(votos))
        return [dict(row._mapping) for row in result]

@app.get("/ranking")
def ranking_api(
    min_media: float = Query(0),
    min_taxa: float = Query(0),
    filtro_aceitacao: str = Query(None)
):
    df = gerar_df_ranking(min_media, min_taxa, filtro_aceitacao)
    return df.to_dict(orient="records")

@app.get("/ranking/predicao")
def ranking_predicao_api(
    min_media: float = Query(0),
    min_taxa: float = Query(0)
):
    df = gerar_df_ranking(min_media, min_taxa)
    # Retorna apenas campos preditivos
    if 'predito_bem_aceito' not in df.columns:
        return []
    return df[['nome', 'predito_bem_aceito']].to_dict(orient="records")

# =============================
# 7. EXECUÇÃO
# =============================
if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
