# Atualizações de Integração com Backend

## Mudanças Implementadas

### 1. Remoção dos Mocks
- Removida a dependência dos mocks de usuários (`usuariosMock.js`) para autenticação e cadastro
- O sistema agora faz chamadas HTTP reais para o backend

### 2. Implementação do HTTP Factory
- Criado `src/services/httpFactory.js` usando Axios
- Configurações padrão:
  - Base URL: `http://localhost:8080`
  - Timeout: 10 segundos
  - Headers padrão: `Content-Type: application/json`
- Interceptors para tratamento de requisições e respostas
- **Interceptor de requisição**: Adiciona automaticamente o token JWT nas requisições
- **Interceptor de resposta**: Trata erros 401 (token expirado) automaticamente

### 3. Serviço de Usuários
- Criado `src/services/userService.js`
- Endpoint implementado:
  - **POST** `/api/v1/users` - Criar usuário
  - Mapeamento atualizado de tipos de usuário:
    - Estudante → STUDENT
    - Funcionário → EMPLOYEE
    - Professor → TEACHER
    - Visitante → VISITOR

### 4. Serviço de Segurança/Autenticação
- Criado `src/services/authSecurityService.js`
- Funcionalidades:
  - **Login**: `POST /api/v1/auth/login`
  - **Logout**: Limpar dados do localStorage
  - **Gerenciamento de token**: Salvar/recuperar token JWT
  - **Gerenciamento de usuário**: Salvar/recuperar dados do usuário
  - **Verificação de autenticação**: Verificar se está logado
  - **Validação de token**: Verificar se o token ainda é válido

### 5. Sistema Global de Notificações (Snackbar)
- Componente `src/components/Snackbar/Snackbar.jsx`
- Context `src/components/Snackbar/SnackbarContext.js`
- Estilos `src/components/Snackbar/Snackbar.css`
- Tipos de notificação: success, error, warning, info
- Posicionamento fixo no canto superior direito
- Auto-dismiss após 5 segundos (configurável)
- Integrado ao App.jsx com SnackbarProvider

### 6. Atualização do Cadastro de Usuário
- `src/pages/CadastroUsuario/CadastroUsuario.jsx` agora usa:
  - **Novos tipos de perfil**: Estudante, Funcionário, Professor, Visitante
  - `userService.createUser()` para criação de usuários
  - Validações de frontend (campos obrigatórios, tamanho da senha)
  - Tratamento de erros específicos do backend
  - Notificações via Snackbar
  - Limpeza automática do formulário após sucesso
  - Redirecionamento automático para login após cadastro

### 7. Atualização do Login
- `src/pages/Login/Login.jsx` agora implementa:
  - Integração completa com `authSecurityService`
  - Autenticação via JWT
  - Salvamento automático de dados de login
  - Notificações de sucesso/erro via Snackbar
  - Estado de carregamento no botão
  - Tratamento específico de erros HTTP

### 8. Persistência de Sessão
- `src/App.jsx` atualizado para:
  - Verificar automaticamente se o usuário já está logado ao iniciar
  - Validar se o token ainda é válido
  - Logout completo (limpar localStorage e redirecionar)
  - Manter estado de autenticação entre recarregamentos da página

## Estrutura de Dados

### CreateUserRequestDto (Backend)
```java
public record CreateUserRequestDto(
    String name,        // nome do usuário
    String email,       // email válido
    String password,    // senha (mín. 6 caracteres no frontend)
    String userRole     // STUDENT, EMPLOYEE, TEACHER, VISITOR
) {
```

### Login Request (Backend)
```java
{
    "email": "user@email.com",      // @NotBlank @Email
    "password": "userpassword"      // @NotEmpty
}
```

### LoginOutput (Backend Response)
```java
public record LoginOutput(
    UserOutput user,
    String token
) {

public record UserOutput(
    UUID id,
    String name,
    String email,
    UserRoleType userRole,
    AccessLevelType accessLevel,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    ZonedDateTime lastAccess
) {
```

## Endpoints Implementados

### Autenticação
- **POST** `/api/v1/auth/login`
  - Body: `{ "email": string, "password": string }`
  - Response: `{ "user": UserOutput, "token": string }`

### Usuários
- **POST** `/api/v1/users`
  - Body: `CreateUserRequestDto`
  - Response: Dados do usuário criado

## Funcionalidades de Segurança

### JWT Token Management
- Token armazenado no localStorage
- Adicionado automaticamente em todas as requisições via interceptor
- Validação de expiração antes de usar
- Limpeza automática em caso de token inválido (401)

### Persistência de Sessão
- Dados do usuário salvos no localStorage
- Verificação automática de login ao iniciar a aplicação
- Logout completo limpa todos os dados

## Como Testar

1. **Iniciar o Backend** na porta 8080
2. **Iniciar o Frontend**: `npm run dev`
3. **Acessar**: http://localhost:5173
4. **Testar Cadastro**:
   - Preencher formulário com novos tipos de perfil
   - Verificar notificações de sucesso/erro
   - Validar criação no backend
5. **Testar Login**:
   - Usar credenciais válidas
   - Verificar salvamento de token e dados
   - Testar persistência (recarregar página)
6. **Testar Logout**:
   - Verificar limpeza de dados
   - Confirmar redirecionamento para login

## Tratamento de Erros

### Cadastro
- **400**: Dados inválidos
- **409**: Email já existe
- **Conexão**: Problemas de rede
- **Servidor**: Erros internos do backend

### Login
- **401**: Credenciais inválidas
- **400**: Dados mal formatados
- **404**: Usuário não encontrado
- **Conexão**: Problemas de rede

### Token JWT
- **401**: Token expirado/inválido (logout automático)
- Interceptor trata automaticamente

Todas as mensagens são exibidas via Snackbar para melhor UX.
