import React, { useState, useEffect } from 'react';
import Login from './pages/Login/Login.jsx';
import CadastroUsuario from './pages/CadastroUsuario/CadastroUsuario.jsx';
import TelaPrincipal from './pages/TelaPrincipal/TelaPrincipal.jsx';
import CadastroEvento from './pages/CadastroEvento/CadastroEvento.jsx';
import TelaAdministrador from './pages/TelaAdministrador/TelaAdministrador.jsx';
import TelaAprovarEvento from './pages/TelaAprovarEvento/TelaAprovarEvento.jsx';
import EventosInscritos from './pages/EventosInscritos/EventosInscritos.jsx';
import VisualizarCertificados from './pages/VisualizarCertificados/VisualizarCertificados.jsx';
import TelaNotificacoes from './pages/TelaNotificacoes/TelaNotificacoes.jsx';
import TelaSugestoes from './pages/TelaSugestoes/TelaSugestoes.jsx';
import TelaMeuPerfil from './pages/TelaMeuPerfil/TelaMeuPerfil.jsx';
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
    const notificationHandler = () => setTela('notificacoes');
    const sugestoesHandler = () => setTela('sugestoes');
    const meuPerfilHandler = () => setTela('meuPerfil');
    
    window.addEventListener('openNotifications', notificationHandler);
    window.addEventListener('openSugestoes', sugestoesHandler);
    window.addEventListener('openMeuPerfil', meuPerfilHandler);
    
    return () => {
      window.removeEventListener('openNotifications', notificationHandler);
      window.removeEventListener('openSugestoes', sugestoesHandler);
      window.removeEventListener('openMeuPerfil', meuPerfilHandler);
    };
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
          <TelaNotificacoes
            usuario={usuarioLogado} 
            onVoltar={() => setTela('principal')}
            onLogout={handleLogout}
            onCadastrarEvento={() => setTela('cadastroEvento')}
            onAbrirAdministrador={() => setTela('administrador')}
            onVisualizarMeusEventos={() => setTela('eventosInscritos')}
            onVisualizarMeusCertificados={() => setTela('visualizarCertificados')}
          />
        )}

        {tela === 'eventosInscritos' && (
          <EventosInscritos
            usuario={usuarioLogado}
            onBack={() => setTela('principal')}
            onVisualizarMeusCertificados={() => setTela('visualizarCertificados')}
            onLogout={handleLogout}
            onCadastrarEvento={() => setTela('cadastroEvento')}
            onAbrirAdministrador={() => setTela('administrador')}
            onVisualizarMeusEventos={() => setTela('eventosInscritos')}
          />
        )}

        {tela === 'visualizarCertificados' && (
          <VisualizarCertificados
            usuario={usuarioLogado}
            onBack={() => setTela('principal')}
            onVisualizarMeusEventos={() => setTela('eventosInscritos')}
            onVisualizarMeusCertificados={() => setTela('visualizarCertificados')}
            onLogout={handleLogout}
            onCadastrarEvento={() => setTela('cadastroEvento')}
            onAbrirAdministrador={() => setTela('administrador')}          
          />
        )}

        {tela === 'sugestoes' && (
          <TelaSugestoes onVoltar={() => setTela('principal')} />
        )}

        {tela === 'meuPerfil' && (
          <TelaMeuPerfil onVoltar={() => setTela('principal')} />
        )}
      </div>
    </SnackbarProvider>
  );
}

export default App;
