import httpFactory from './httpFactory';

export const authSecurityService = {
  login: async (email, password) => {
    const response = await httpFactory.post('/api/v1/auth/login', {
      email,
      password
    });
    return response.data;
  },

  logout: async () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  saveAuthData: (loginOutput) => {
    localStorage.setItem('token', loginOutput.token);
    // persist user and explicit role/accessLevel for easier client-side routing
    const toStore = {
      ...loginOutput.user,
      userRole: loginOutput.role,
      accessLevel: loginOutput.accessLevel
    };
    localStorage.setItem('user', JSON.stringify(toStore));
  },

  getToken: () => {
    return localStorage.getItem('token');
  },

  getUser: () => {
    const userData = localStorage.getItem('user');
    return userData ? JSON.parse(userData) : null;
  },

  isAuthenticated: () => {
    const token = localStorage.getItem('token');
    const user = localStorage.getItem('user');
    return !!(token && user);
  },

  isTokenValid: () => {
    const token = localStorage.getItem('token');
    if (!token) return false;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Date.now() / 1000;
      return payload.exp > currentTime;
    } catch {
      return false;
    }
  }
};

export default authSecurityService;
