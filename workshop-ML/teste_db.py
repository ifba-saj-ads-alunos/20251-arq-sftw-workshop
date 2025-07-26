from sqlalchemy import create_engine, text
from pathlib import Path

DB_FILE = Path(__file__).parent / "fallback.db"
engine = create_engine(f"sqlite:///{DB_FILE}", future=True)

with engine.begin() as conn:
    conn.execute(text("PRAGMA foreign_keys = ON"))

    # Cria as tabelas se não existirem
    conn.execute(text("""
        CREATE TABLE IF NOT EXISTS apresentacoes (
            id INTEGER PRIMARY KEY,
            inscritos INTEGER NOT NULL DEFAULT 0
        )
    """))
    
    conn.execute(text("""
        CREATE TABLE IF NOT EXISTS votos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            apresentacao_id INTEGER NOT NULL,
            nota_geral REAL NOT NULL,
            FOREIGN KEY(apresentacao_id) REFERENCES apresentacoes(id)
        )
    """))

    # Dados de teste
    if conn.execute(text("SELECT COUNT(*) FROM apresentacoes")).scalar() == 0:
        conn.execute(text("""
            INSERT INTO apresentacoes (id, inscritos) VALUES
            (1, 50), (2, 40), (3, 60)
        """))
        
        conn.execute(text("""
            INSERT INTO votos (apresentacao_id, nota_geral) VALUES
            (1, 4.5), (1, 5.0),
            (2, 3.0),
            (3, 4.0), (3, 3.5)
        """))
        print(" Banco de dados inicializado com dados de teste")
    else:
        print(" Banco já populado. Nenhum dado inserido.")

# Adicionar dados para teste em tempo real
print("\n Digite comandos para adicionar dados.")
print("Comandos:")
print("  nova_apresentacao <id> <inscritos>")
print("  novo_voto <apresentacao_id> <nota_geral>")
print("  sair")

while True:
    comando = input(">>> ").strip()
    
    if comando == "sair":
        print("👋 Encerrando...")
        break

    try:
        with engine.begin() as conn:
            if comando.startswith("nova_apresentacao"):
                _, id_str, inscritos_str = comando.split()
                conn.execute(text("INSERT INTO apresentacoes (id, inscritos) VALUES (:id, :inscritos)"),
                             {"id": int(id_str), "inscritos": int(inscritos_str)})
                print(" Apresentação adicionada.")

            elif comando.startswith("novo_voto"):
                _, apresentacao_id_str, nota_str = comando.split()
                conn.execute(text("INSERT INTO votos (apresentacao_id, nota_geral) VALUES (:ap_id, :nota)"),
                             {"ap_id": int(apresentacao_id_str), "nota": float(nota_str)})
                print(" Voto adicionado.")

            else:
                print("Comando não reconhecido.")

    except Exception as e:
        print(f"Erro ao executar comando: {e}")
