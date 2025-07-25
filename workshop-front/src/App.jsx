import React, { useState } from 'react';
import Login from './pages/Login/Login.jsx';
import CadastroUsuario from './pages/CadastroUsuario/CadastroUsuario.jsx';
import TelaPrincipal from './pages/TelaPrincipal/TelaPrincipal.jsx';
import CadastroEvento from './pages/CadastroEvento/CadastroEvento.jsx';
import TelaAdministrador from './pages/TelaAdministrador/TelaAdministrador.jsx';
import TelaAprovarEvento from './pages/TelaAprovarEvento/TelaAprovarEvento.jsx';
import EventosInscritos from './pages/EventosInscritos/EventosInscritos.jsx';
import VisualizarCertificados from './pages/VisualizarCertificados/VisualizarCertificados.jsx';

function App() {
  const [tela, setTela] = useState('login');
  const [usuarioLogado, setUsuarioLogado] = useState(null);

  const handleLoginSuccess = (usuario) => {
    setUsuarioLogado(usuario);
    setTela('principal');
  };

  const handleLogout = () => {
    setUsuarioLogado(null);
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
          usuario={usuarioLogado}
          onLogout={handleLogout}
          onCadastrarEvento={() => setTela('cadastroEvento')}
          onAbrirAdministrador={() => setTela('administrador')}
          onVisualizarMeusEventos={() => setTela('eventosInscritos')}
          onVisualizarMeusCertificados={() => setTela('visualizarCertificados')}
        />
      )}

      {tela === 'cadastroEvento' && (
        <CadastroEvento
          usuario={usuarioLogado}
          onBack={() => setTela('principal')}
        />
      )}

      {tela === 'administrador' && (
        <TelaAdministrador
          usuario={usuarioLogado}
          onVoltar={() => setTela('principal')}
          onAprovarEvento={() => setTela('aprovarEvento')}
        />
      )}

      {tela === 'aprovarEvento' && (
        <TelaAprovarEvento
          usuario={usuarioLogado}
          onVoltar={() => setTela('administrador')}
        />
      )}

      {tela === 'eventosInscritos' && (
        <EventosInscritos
          usuario={usuarioLogado}
          onBack={() => setTela('principal')}
          onVisualizarMeusCertificados={() => setTela('visualizarCertificados')}/>
      )}

      {tela === 'visualizarCertificados' && (
        <VisualizarCertificados
          usuario={usuarioLogado}
          onBack={() => setTela('principal')}
          onVisualizarMeusEventos={() => setTela('eventosInscritos')} />
      )}
    </div>
  );
}

export default App;
