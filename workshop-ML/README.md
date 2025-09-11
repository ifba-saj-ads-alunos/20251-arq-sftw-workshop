# 🎓 Sistema de Ranking de Apresentações – WEP IFBA

Este projeto implementa uma API web utilizando **FastAPI** que gerencia apresentações avaliadas por votos e gera um ranking inteligente, com análise de aceitação baseada em **KMeans** e predição de apresentações bem aceitas.

## 🚀 Funcionalidades

- Cadastro de apresentações e votos (armazenados no banco).
- **Endpoint `/ranking`** que:
  - Agrupa dados por apresentação.
  - Calcula média de votos e taxa de participação.
  - Usa KMeans para identificar a aceitação (baixa, média ou alta).
  - Extrai palavras-chave do título da apresentação.
  - Prediz se a apresentação será bem aceita usando **RandomForest**.
- **Endpoint `/ranking/predicao`** que retorna apenas o nome das apresentações e o resultado da predição.
- **Endpoint `/debug`** que retorna todos os votos no banco (para depuração).
- Suporte a vários bancos de dados e fallback automático para SQLite.

## 🛠️ Tecnologias Usadas

- **FastAPI** – framework para criação de APIs.
- **SQLAlchemy** – ORM e SQL Toolkit.
- **SQLite / PostgreSQL** – banco de dados.
- **Pandas** – análise de dados.
- **NumPy** – operações numéricas.
- **scikit-learn** – aprendizado de máquina (KMeans e RandomForest).
- **Uvicorn** – servidor ASGI para rodar a API.

## 📄 Endpoints da API

### GET `/ranking`

Calcula o ranking das apresentações com base nos votos, retornando uma lista com:

- `nome`: título da apresentação  
- `inscritos`: número de inscritos  
- `votantes`: número de votos recebidos  
- `media_estrelas`: média das notas  
- `taxa_participacao`: votantes / inscritos  
- `posicao`: ranking  
- `aceitacao`: baixa, média ou alta  
- `palavras_chave`: palavras-chave extraídas do título  
- `predito_bem_aceito`: true ou false indicando se o modelo prevê boa aceitação  

**Exemplo de resposta:**

```json
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



