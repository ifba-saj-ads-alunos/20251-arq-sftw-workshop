const mockUsuarios = [
  { email: 'usuario1@ifba.edu.br', senha: '123456' },
  { email: 'admin@ifba.edu.br', senha: 'admin123' },
  { email: '1@1', senha: '1'},
];

export function verificarEmailExiste(email) {
  return mockUsuarios.some(usuario => usuario.email === email);
}

export function autenticar(email, senha) {
  return mockUsuarios.some(usuario => usuario.email === email && usuario.senha === senha);
}

// este arquivo simula um banco e oferece funções reutilizáveis para autenticação e verificação de email.