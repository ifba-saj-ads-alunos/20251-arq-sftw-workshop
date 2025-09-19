# 20251-arq-sftw-workshop
Projeto de arquitetura de software que visa sistema de eventos/workshop no IFBA

## Como rodar o projeto

### Pré-requisitos
- Java 17+ para o backend
- Node.js e npm para o frontend  
- Docker para o banco de dados

### Passos para execução

1. **Abra um terminal (de preferência Linux ou WSL)**

2. **Navegue até a pasta do projeto:**
   ```bash
   cd 20251-arq-sftw-workshop
   ```

3. **Build do Backend:**
   ```bash
   cd workshop-backend
   mvn clean install
   cd ..
   ```

4. **Subir o banco de dados com Docker:**
   ```bash
   docker-compose up -d
   ```

5. **Executar o backend:**
   ```bash
   cd workshop-backend/workshop-web
   mvn spring-boot:run
   ```

6. **Em outro terminal, instalar dependências e executar o frontend:**
   ```bash
   cd workshop-front
   npm install
   npm run dev
   ```

7. **Acessar a aplicação:**
   - Frontend: http://localhost:5173
   - Backend: http://localhost:8080

## Tecnologias utilizadas

### Backend
* Java 17
* Spring Boot 3.5.0
* Maven
* PostgreSQL

### Frontend
* Node.js
* React
* Vite

### ML Service (Opcional)
* Python
* FastAPI