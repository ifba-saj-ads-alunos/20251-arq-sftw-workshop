🎓 Sistema de Ranking de Apresentações – WEP IFBA

Este projeto implementa uma API web utilizando FastAPI que gerencia apresentações avaliadas por votos e gera um ranking inteligente, com análise de aceitação baseada em KMeans e predição de apresentações bem aceitas.

🚀 Funcionalidades

Cadastro de apresentações e votos (armazenados no banco).

Endpoint /ranking que:

Agrupa dados por apresentação.

Calcula média de votos e taxa de participação.

Usa KMeans para identificar a aceitação (baixa, média ou alta).

Extrai palavras-chave do título da apresentação.

Prediz se a apresentação será bem aceita usando RandomForest.

Endpoint /ranking/predicao que retorna apenas o nome das apresentações e o resultado da predição.

Endpoint /debug que retorna todos os votos no banco (para depuração).

Suporte a vários bancos de dados e fallback automático para SQLite.

🛠️ Tecnologias Usadas

FastAPI – framework para criação de APIs.

SQLAlchemy – ORM e SQL Toolkit.

SQLite / PostgreSQL – banco de dados.

Pandas – análise de dados.

NumPy – operações numéricas.

scikit-learn – aprendizado de máquina (KMeans e RandomForest).

Uvicorn – servidor ASGI para rodar a API.

📄 Endpoints da API
GET /ranking

Calcula o ranking das apresentações com base nos votos, retornando uma lista com:

nome: título da apresentação

inscritos: número de inscritos

votantes: número de votos recebidos

media_estrelas: média das notas

taxa_participacao: votantes / inscritos

posicao: ranking

aceitacao: baixa, média ou alta

palavras_chave: palavras-chave extraídas do título

predito_bem_aceito: true ou false indicando se o modelo prevê boa aceitação

Exemplo de resposta:

[
  {
    "nome": "Apresentação X",
    "inscritos": 50,
    "votantes": 2,
    "media_estrelas": 4.75,
    "taxa_participacao": 0.04,
    "posicao": 1,
    "aceitacao": "alta aceitação",
    "palavras_chave": ["apresentacao", "x"],
    "predito_bem_aceito": true
  }
]

GET /ranking/predicao

Retorna apenas o nome das apresentações e a previsão de boa aceitação:

[
  {
    "nome": "Apresentação X",
    "predito_bem_aceito": true
  }
]

GET /debug

Retorna todos os votos cadastrados no banco, para fins de teste e depuração.

🧠 Lógica do Ranking

Join entre apresentacoes e votos usando SQLAlchemy.

Cálculo de média de estrelas e taxa de participação (votantes / inscritos).

Se houver pelo menos 3 apresentações:

Aplicação do StandardScaler para normalizar os dados.

Agrupamento via KMeans (3 grupos).

Cada grupo recebe uma etiqueta de aceitação: alta, média ou baixa.

Caso contrário, assume-se "média aceitação".

Extração de palavras-chave do título da apresentação.

Treinamento de modelo RandomForest para predição de boas apresentações.

🗃️ Banco de Dados
Tabelas utilizadas

apresentacoes:

id (PK)

titulo: título da apresentação

inscritos: número de inscritos na apresentação

votos:

id (PK)

apresentacao_id (FK → apresentacoes.id)

nota_geral: nota dada por um votante

Inicialização automática (SQLite)

Se o banco estiver vazio, será utilizado fallback.db.

🔄 Fallback para SQLite

Caso nenhum banco de dados esteja disponível, será utilizado automaticamente um banco local SQLite chamado fallback.db.

✅ Requisitos

Python 3.9+

Pacotes listados em requirements.txt:

fastapi

uvicorn

sqlalchemy

pandas

scikit-learn

numpy
