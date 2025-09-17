import React, { useState, useEffect } from 'react';
import Login from './pages/Login/Login.jsx';
import CadastroUsuario from './pages/CadastroUsuario/CadastroUsuario.jsx';
import TelaPrincipal from './pages/TelaPrincipal/TelaPrincipal.jsx';
import CadastroEvento from './pages/CadastroEvento/CadastroEvento.jsx';
import TelaAdministrador from './pages/TelaAdministrador/TelaAdministrador.jsx';
import TelaAprovarEvento from './pages/TelaAprovarEvento/TelaAprovarEvento.jsx';
import TelaNotificacoes from './pages/TelaNotificacoes/TelaNotificacoes.jsx';
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
        // Decide initial screen based on access level or role
        const screen = decideScreenByRole(userData);
        setTela(screen);
      }
    }
  }, []);

  useEffect(() => {
    const handler = () => setTela('notificacoes');
    window.addEventListener('openNotifications', handler);
    return () => window.removeEventListener('openNotifications', handler);
  }, []);

  const handleLoginSuccess = (usuario) => {
    setUsuarioLogado(usuario);
    const screen = decideScreenByRole(usuario);
    setTela(screen);
  };

  function decideScreenByRole(user) {
    if (!user) return 'login';
    // accessLevel may be 'ADMIN' or 'USER'
    const accessLevel = typeof user.accessLevel === 'string' ? user.accessLevel : (user.accessLevel?.name || user.accessLevel);
    if (accessLevel === 'ADMIN') return 'administrador';

    // userRole may be a string like 'STUDENT', or an object/enum representation
    const userRole = typeof user.userRole === 'string' ? user.userRole : (user.userRole?.name || user.userRole);
    if (userRole === 'STUDENT') return 'principal';

    // default for other roles
    return 'principal';
  }

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

        {tela === 'notificacoes' && (
          <TelaNotificacoes onVoltar={() => setTela('principal')} />
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
