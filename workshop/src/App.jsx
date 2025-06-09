import React, { useState } from 'react';
import Login from './pages/Login';
import CadastroUsuario from './pages/CadastroUsuario';
import TelaPrincipal from './pages/TelaPrincipal';

export default function App() {
  const [tela, setTela] = useState('login');

  const handleLoginSuccess = () => {
    setTela('principal');
  };

  const handleCadastroClick = () => {
    setTela('cadastro');
  };

  const handleBackToLogin = () => {
    setTela('login');
  };

  const handleLogout = () => {
    setTela('login');
  };

  return (
    <>
      {tela === 'login' && (
        <Login 
          onCadastroClick={handleCadastroClick} 
          onLoginSuccess={handleLoginSuccess}
        />
      )}

      {tela === 'cadastro' && (
        <CadastroUsuario onBack={handleBackToLogin} />
      )}

      {tela === 'principal' && (
        <TelaPrincipal onLogout={handleLogout} />
      )}
    </>
  );
}
