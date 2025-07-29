import httpFactory from './httpFactory';

export async function verificarEmailExiste(email) {
  try {
    const response = await httpFactory.get(`/api/v1/users/check-email?email=${email}`);
    return response.data.exists;
  } catch {
    return false;
  }
}

export async function autenticar(email, senha) {
  const response = await httpFactory.post('/api/v1/auth/login', {
    email,
    password: senha
  });
  return response.data;
}

export async function obterUsuarioPorEmail(email) {
  try {
    const response = await httpFactory.get(`/api/v1/users/by-email?email=${email}`);
    return response.data;
  } catch {
    return null;
  }
}