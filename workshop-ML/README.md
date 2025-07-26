````markdown
# 🎓 Sistema de Ranking de Apresentações – WEP IFBA

Este projeto implementa uma API web utilizando *FastAPI* que gerencia apresentações avaliadas por votos e gera um *ranking inteligente*, com análise de aceitação baseada em clustering com *KMeans*.

---

## 🚀 Funcionalidades

- Cadastro de apresentações e votos (armazenados no banco).
- Endpoint `/ranking` que:
  - Agrupa dados por apresentação.
  - Calcula média de votos e taxa de participação.
  - Usa *KMeans* para identificar a aceitação (baixa, média ou alta).
- Endpoint `/debug` que retorna todos os votos no banco (para depuração).
- Suporte a varios bancos de dados e utiliza o  *SQLite (fallback)*.

---

## 🛠️ Tecnologias Usadas

- [FastAPI] - framework para criação de APIs.
- [SQLAlchemy] — ORM e SQL Toolkit.
- [SQLite / PostgreSQL] — banco de dados.
- [Pandas] — análise de dados.
- [NumPy] — operações numéricas.
- [scikit-learn] — aprendizado de máquina (KMeans).
- [Uvicorn] — servidor ASGI para rodar a API.

---

## 📄 Endpoints da API

### `GET /ranking`

Calcula o ranking das apresentações com base nos votos, retornando uma lista com:

* `apresentacao_id`
* `inscritos`
* `votantes`
* `media_estrelas`
* `taxa_participacao`
* `posicao` (ranking)
* `aceitacao` (baixa, média ou alta)

Exemplo de resposta:

```json
[
  {
    "apresentacao_id": 1,
    "inscritos": 50,
    "votantes": 2,
    "media_estrelas": 4.75,
    "taxa_participacao": 0.04,
    "posicao": 1,
    "aceitacao": "alta aceitação"
  }
]
```

---

### `GET /debug`

Retorna todos os votos cadastrados no banco, para fins de teste e depuração.

---

## 🧠 Lógica do Ranking

1. **Join entre `apresentacoes` e `votos`** usando SQLAlchemy.
2. **Cálculo de média de estrelas** e **taxa de participação** (`votantes / inscritos`).
3. Se houver pelo menos 3 apresentações:

   * Aplicação do **StandardScaler** para normalizar os dados.
   * Agrupamento via **KMeans (3 grupos)**.
   * Cada grupo recebe uma etiqueta de aceitação: alta, média ou baixa.
4. Caso contrário, assume-se "média aceitação" por padrão.

---

## 🗃️ Banco de Dados

### Tabelas utilizadas:

* `apresentacoes`:

  * `id` (PK)
  * `inscritos`: número de inscritos na apresentação

* `votos`:

  * `id` (PK)
  * `apresentacao_id` (FK → apresentacoes.id)
  * `nota_geral`: nota dada por um votante

### Inicialização automática (SQLite):

Se o banco estiver vazio, ele será populado com dados de teste.

---

## 🔄 Fallback para SQLite

Caso nenhum banco de dados esteja disponível, será utilizado automaticamente um banco local SQLite chamado `fallback.db`.

---

## ✅ Requisitos

* Python 3.9+
* Pacotes listados em `requirements.txt`, incluindo:

  * `fastapi`
  * `uvicorn`
  * `sqlalchemy`
  * `pandas`
  * `scikit-learn`
  * `numpy`

---
