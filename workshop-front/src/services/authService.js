import usuariosMock from '../mocks/usuariosMock';

export function verificarEmailExiste(email) {
  return usuariosMock.some(usuario => usuario.email === email);
}

export function autenticar(email, senha) {
  return usuariosMock.some(usuario => usuario.email === email && usuario.senha === senha);
}

export function obterUsuarioPorEmail(email) {
  return usuariosMock.find(usuario => usuario.email === email);
}

// este arquivo simula um banco e oferece funções reutilizáveis para autenticação e verificação de email.