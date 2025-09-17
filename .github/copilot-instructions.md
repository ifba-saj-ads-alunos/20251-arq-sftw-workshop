# Copilot Instructions for AI Coding Agents

## Project Overview
This monorepo implements an event/workshop management system for IFBA, with a modular architecture:
- **workshop-backend/**: Java (Maven) backend, split into `workshop-core` (domain, use cases), `workshop-infra` (infrastructure), and `workshop-web` (web layer).
- **workshop-front/**: React + Vite frontend, integrates with backend APIs.
- **workshop-ML/**: Python FastAPI microservice for presentation ranking and ML predictions.

## Key Workflows
- **Backend (Java/Maven):**
  - Build all modules: `mvn clean install` from `workshop-backend/`
  - Run tests: `mvn test`
  - Artifacts: JARs in each module's `target/`
- **Frontend (React/Vite):**
  - Start dev server: `npm run dev` in `workshop-front/`
  - Build: `npm run build`
- **ML Service (FastAPI):**
  - Install deps: `pip install -r requirements.txt` in `workshop-ML/`
  - Run: `uvicorn main:app --reload`

## Conventions & Patterns
- **Backend:**
  - Use case implementations in `workshop-core/src/main/java/.../usecases/`
  - Domain models in `.../domain/models/`
  - Tests in `src/test/java/`
- **Frontend:**
  - Components in `src/components/`, pages in `src/pages/`, services in `src/services/`
  - Mock data in `src/mocks/`
- **ML:**
  - Endpoints: `/ranking`, `/ranking/predicao`, `/debug` (see `workshop-ML/README.md`)
  - Fallback to SQLite if DB unavailable

## Integration Points
- Frontend calls backend REST APIs and ML FastAPI endpoints
- Backend and ML services are decoupled; communicate via HTTP
- Use Docker Compose for local multi-service orchestration (see `docker-compose.yaml`)

## Project-Specific Notes
- Prefer Linux/WSL for running scripts and builds
- Artifacts and test reports are in each module's `target/` directory
- Follow existing directory structure and naming conventions for new modules/components

## References
- See `README.md` in root, `workshop-front/`, and `workshop-ML/` for more details and examples
- Key files: `docker-compose.yaml`, `pom.xml` (per module), `requirements.txt`, `vite.config.js`

---
For questions about architecture or workflows, review the referenced READMEs and module structure before making changes.