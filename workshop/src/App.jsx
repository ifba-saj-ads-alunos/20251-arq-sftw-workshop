import React, { useState } from 'react';
import Login from './pages/Login';
import CadastroUsuario from './pages/CadastroUsuario';

function App() {
  const [currentPage, setCurrentPage] = useState('login');

  return (
    <>
      {currentPage === 'login' && (
        <Login onCadastroClick={() => setCurrentPage('cadastro')} />
      )}

      {currentPage === 'cadastro' && (
        <CadastroUsuario onBack={() => setCurrentPage('login')} />
      )}
    </>
  );
}

export default App;
