import React, { useState } from 'react';
import './Login.css';
import logo from '../../assets/ifba_logo.png';
import PasswordResetModal from '../../components/PasswordResetModal/PasswordResetModal.jsx';
import { verificarEmailExiste, autenticar } from '../../services/authService';

export default function Login({ onCadastroClick, onLoginSuccess }) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erroLogin, setErroLogin] = useState('');
  const [mostrarModal, setMostrarModal] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!verificarEmailExiste(email)) {
      setErroLogin('Endereço de email não encontrado');
    } else if (!autenticar(email, senha)) {
      setErroLogin('Senha incorreta');
    } else {
      setErroLogin('');
      onLoginSuccess();
    }
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
        />

        <label htmlFor="senha">Senha</label>
        <input
          id="senha"
          type="password"
          placeholder="********"
          required
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />

        <button type="submit" className="login-btn">Entrar</button>
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
