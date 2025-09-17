import httpFactory from './httpFactory';

const normalizeCpf = (cpf) => (cpf ?? '').replace(/\D/g, '');

export const userService = {
  createUser: async (userData) => {
    const response = await httpFactory.post('/api/v1/users', {
      name: userData.name,
      cpf: normalizeCpf(userData.cpf),
      email: userData.email,
      password: userData.password,
      userRole: userData.userRole
    });
    return response.data;
  },

  checkEmailExists: async (email) => {
    const response = await httpFactory.get(`/api/v1/users/check-email?email=${email}`);
    return response.data;
  },

  login: async (email, password) => {
    const response = await httpFactory.post('/api/v1/auth/login', {
      email,
      password
    });
    return response.data;
  }
};

export default userService;
