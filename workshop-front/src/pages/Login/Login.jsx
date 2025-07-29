import React, { useState } from 'react';
import './Login.css';
import logo from '../../assets/ifba_logo.png';
import PasswordResetModal from '../../components/PasswordResetModal/PasswordResetModal.jsx';
import { useSnackbar } from '../../components/Snackbar/SnackbarContext';
import authSecurityService from '../../services/authSecurityService';

export default function Login({ onCadastroClick, onLoginSuccess }) {
  const { showError, showSuccess } = useSnackbar();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erroLogin, setErroLogin] = useState('');
  const [mostrarModal, setMostrarModal] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErroLogin('');

    if (!email || !senha) {
      setErroLogin('Por favor, preencha email e senha');
      showError('Por favor, preencha todos os campos');
      setLoading(false);
      return;
    }

    try {
      const loginOutput = await authSecurityService.login(email, senha);
      
      authSecurityService.saveAuthData(loginOutput);
      
      showSuccess(`Bem-vindo(a), ${loginOutput.user.name}!`);
      setErroLogin('');
      
      onLoginSuccess(loginOutput.user);
      
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        
        if (status === 401) {
          setErroLogin('Email ou senha incorretos');
          showError('Email ou senha incorretos');
        } else if (status === 400) {
          setErroLogin('Dados inválidos');
          showError(data.message || 'Dados inválidos');
        } else if (status === 404) {
          setErroLogin('Usuário não encontrado');
          showError('Usuário não encontrado');
        } else {
          setErroLogin('Erro no servidor');
          showError('Erro no servidor. Tente novamente mais tarde.');
        }
      } else if (error.request) {
        setErroLogin('Erro de conexão');
        showError('Erro de conexão. Verifique sua internet.');
      } else {
        setErroLogin('Erro inesperado');
        showError('Erro inesperado. Tente novamente.');
      }
    }

    setLoading(false);
  };

  const verificarEmailExiste = async () => {
    return true;
  };

  return (
    <div className="login-container">
      <img
        src={logo}
        alt="Logo"
        className="logo"
        style={{ display: 'block', margin: '0 auto 1rem auto', maxWidth: '150px' }}
      />
      <form onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          placeholder="seu@email.com"
          required
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className='campos-login'
        />

        <label htmlFor="senha">Senha</label>
        <input
          id="senha"
          type="password"
          placeholder="********"
          required
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          className='campos-login'
        />

        <button type="submit" className="login-btn" disabled={loading}>
          {loading ? 'Entrando...' : 'Entrar'}
        </button>
        {erroLogin && <p style={{ color: 'red', fontSize: '0.9rem' }}>{erroLogin}</p>}
      </form>

      <button className="secondary-btn" onClick={() => setMostrarModal(true)}>
        Esqueci minha senha
      </button>

      <button className="secondary-btn" onClick={onCadastroClick}>
        Cadastre-se
      </button>

      <button className="secondary-btn">
        Validar certificado
      </button>

      {mostrarModal && (
        <PasswordResetModal
          onClose={() => setMostrarModal(false)}
          verificarEmailExiste={verificarEmailExiste}
        />
      )}
    </div>
  );
}
