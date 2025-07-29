import React, { useState, useEffect } from 'react';
import Login from './pages/Login/Login.jsx';
import CadastroUsuario from './pages/CadastroUsuario/CadastroUsuario.jsx';
import TelaPrincipal from './pages/TelaPrincipal/TelaPrincipal.jsx';
import CadastroEvento from './pages/CadastroEvento/CadastroEvento.jsx';
import TelaAdministrador from './pages/TelaAdministrador/TelaAdministrador.jsx';
import TelaAprovarEvento from './pages/TelaAprovarEvento/TelaAprovarEvento.jsx';
import EventosInscritos from './pages/EventosInscritos/EventosInscritos.jsx';
import VisualizarCertificados from './pages/VisualizarCertificados/VisualizarCertificados.jsx';
import { SnackbarProvider } from './components/Snackbar/Snackbar.jsx';
import authSecurityService from './services/authSecurityService';

function App() {
  const [tela, setTela] = useState('login');
  const [usuarioLogado, setUsuarioLogado] = useState(null);

  useEffect(() => {
    if (authSecurityService.isAuthenticated() && authSecurityService.isTokenValid()) {
      const userData = authSecurityService.getUser();
      if (userData) {
        setUsuarioLogado(userData);
        setTela('principal');
      }
    }
  }, []);

  const handleLoginSuccess = (usuario) => {
    setUsuarioLogado(usuario);
    setTela('principal');
  };

  const handleLogout = () => {
    authSecurityService.logout();
    setUsuarioLogado(null);
    setTela('login');
  };

  return (
    <SnackbarProvider>
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
    </SnackbarProvider>
  );
}

export default App;
