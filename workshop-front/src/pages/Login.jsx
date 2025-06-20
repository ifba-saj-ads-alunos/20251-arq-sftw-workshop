import React from 'react';
import '../styles/Login.css';
import logo from '../assets/ifba_logo.png';

export default function Login({ onCadastroClick, onLoginSuccess }) {

  const handleSubmit = (e) => {
    e.preventDefault();
    onLoginSuccess();
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
        <input id="email" type="email" placeholder="seu@email.com" required />

        <label htmlFor="senha">Senha</label>
        <input id="senha" type="password" placeholder="********" required />

        <button type="submit" className="login-btn">Entrar</button>
      </form>

      <button className="secondary-btn" style={{ marginBottom: '0.5rem' }}>
        Esqueci minha senha
      </button>

      <button
        className="secondary-btn"
        style={{ marginBottom: '0.5rem' }}
        onClick={onCadastroClick}
      >
        Cadastre-se
      </button>

      <button className="secondary-btn">
        Validar certificado
      </button>
    </div>
  );
}
