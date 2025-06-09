import React, { useState } from 'react';
import Login from './pages/Login';
import CadastroUsuario from './pages/CadastroUsuario';
import TelaPrincipal from './pages/TelaPrincipal';
import CadastroEvento from './pages/CadastroEvento';

function App() {
  const [tela, setTela] = useState('login');

  const handleLoginSuccess = () => {
    setTela('principal');
  };

  const handleLogout = () => {
    setTela('login');
  };

  return (
    <div>
      {tela === 'login' && (
        <Login
          onCadastroClick={() => setTela('cadastroUsuario')}
          onLoginSuccess={handleLoginSuccess}
        />
      )}

      {tela === 'cadastroUsuario' && (
        <CadastroUsuario onBack={() => setTela('login')} />
      )}

      {tela === 'principal' && (
        <TelaPrincipal
          onLogout={handleLogout}
          onCadastrarEvento={() => setTela('cadastroEvento')}
        />
      )}

      {tela === 'cadastroEvento' && (
        <CadastroEvento onBack={() => setTela('principal')} />
      )}
    </div>
  );
}

export default App;
