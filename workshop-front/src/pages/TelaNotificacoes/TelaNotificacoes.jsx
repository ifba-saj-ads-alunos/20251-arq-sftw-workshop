import React, { useEffect, useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaNotificacoes.css';
import logo from '../../assets/ifba_logo.png';
import { FaBars, FaBell, FaClipboardList, FaCertificate, FaTrophy } from 'react-icons/fa';
import { fetchUnreadCount, fetchNotifications, markAsRead, markAllRead } from '../../services/notificationService';

export default function TelaNotificacoes({ usuario, onVoltar, onLogout, onCadastrarEvento, onAbrirAdministrador, onVisualizarMeusEventos, onVisualizarMeusCertificados }) {
  const [notificacoes, setNotificacoes] = useState([]);
  const [menuAberto, setMenuAberto] = useState(false);
  const [unread, setUnread] = useState(0);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchNotifications();
        const list = data || [];
        setNotificacoes(list);

        // do not auto-mark here anymore; we'll provide per-item click and a button
      } catch (err) {
        console.error(err);
      }
    })();
  }, []);

  const handleClickNotification = async (n) => {
    try {
      if (!n.read) {
        await markAsRead(n.id);
        setNotificacoes(prev => prev.map(item => item.id === n.id ? { ...item, read: true } : item));
        // notify other components (e.g., header) to refresh unread count
        window.dispatchEvent(new CustomEvent('notificationsUpdated'));
      }
    } catch (err) {
      console.error('Erro ao marcar notificação como lida', err);
    }
  };

  const handleMarkAll = async () => {
    try {
      await markAllRead();
      setNotificacoes(prev => prev.map(n => ({ ...n, read: true })));
      window.dispatchEvent(new CustomEvent('notificationsUpdated'));
    } catch (err) {
      console.error('Erro ao marcar todas como lidas', err);
    }
  };

  const toggleMenu = () => setMenuAberto(!menuAberto);

  const getUserRole = () => {
    if (!usuario) return 'VISITOR';
    return typeof usuario.userRole === 'string' ? usuario.userRole : (usuario.userRole?.name || usuario.userRole || 'VISITOR');
  };
  const userRole = getUserRole();

  return (
    <div className="tela-container">
      <div className="principal-container">
        <div className="menu-hamburguer" onClick={toggleMenu}>
          <FaBars size={24} />
        </div>

        {menuAberto && <div className="overlay" onClick={() => setMenuAberto(false)} />}

        {menuAberto && (
          <div className="menu-lateral">
            <button onClick={() => {
              setMenuAberto(false);
              if (userRole === 'VISITOR') {
                // Navigate to profile page for visitors
                window.dispatchEvent(new CustomEvent('openMeuPerfil'));
              } else {
                alert('Meu Perfil');
              }
            }}>Meu Perfil</button>
            
            {/* Only show Cadastrar Evento for non-visitors */}
            {userRole !== 'VISITOR' && (
              <button onClick={() => {
                setMenuAberto(false);
                onCadastrarEvento();
              }}>Cadastrar Evento</button>
            )}
            
            {/* Only show Administrador for non-visitors */}
            {userRole !== 'VISITOR' && (
              <button onClick={() => {
                setMenuAberto(false);
                onAbrirAdministrador();
              }}>
                Administrador
              </button>
            )}
            
            <button onClick={() => {
              setMenuAberto(false);
              if (userRole === 'VISITOR') {
                // Navigate to suggestions page for visitors
                window.dispatchEvent(new CustomEvent('openSugestoes'));
              } else {
                alert('Sugestões');
              }
            }}>Sugestões</button>
            <button onClick={onLogout}>Logout</button>
          </div>
        )}

        <div className="topo-pagina">
          <img 
            src={logo}
            alt="Logo" 
            className="logo tela-principal"
          />

          <div className="lista-botoes tela-principal">
            <div className="btn-notificacao-wrapper" style={{position: 'relative'}}>
              <button className="principal-btn tela-principal btn-icone selected-screen" onClick={() => window.dispatchEvent(new CustomEvent('openNotifications'))}>
                <span className="icone-btn"><FaBell /></span>
                <span className="texto-btn">Notificações</span>
              </button>
              {unread > 0 && (
                <span className="badge-unread">{unread}</span>
              )}
            </div>
            <button className="principal-btn tela-principal btn-icone" onClick={onVisualizarMeusEventos}>
              <span className="icone-btn"><FaClipboardList /></span>
              <span className="texto-btn">Inscrições</span>
            </button>
            <button className="principal-btn tela-principal btn-icone" onClick={onVisualizarMeusCertificados}>
              <span className="icone-btn"><FaCertificate /></span>
              <span className="texto-btn">Certificados</span>
            </button>
            <button className="principal-btn tela-principal btn-icone">
              <span className="icone-btn"><FaTrophy /></span>
              <span className="texto-btn">Ranking</span>
            </button>
          </div>
        </div>

        <div className="notificacoes-header">
          <h2 id="h2-notificacoes">Notificações</h2>
          <button className="secondary-btn btn-voltar notificacoes" onClick={onVoltar}>Voltar</button>
        </div>

        <div className="notificacoes-list">
          {notificacoes.length === 0 ? (
            <p>Nenhuma notificação.</p>
          ) : (
            <>
              <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 8 }}>
                <button className="secondary-btn" onClick={handleMarkAll}>Marcar todas como lidas</button>
              </div>
              {notificacoes.map(n => (
                <div key={n.id} className={`notificacao-item ${n.read ? 'read' : 'unread'}`} onClick={() => handleClickNotification(n)} style={{ cursor: 'pointer' }}>
                  <h4>{n.title} {n.read ? null : <span className="dot-unread" />}</h4>
                  <p>{n.message}</p>
                  <small>{n.sentAt}</small>
                </div>
              ))}
            </>
          )}
        </div>
      </div>
    </div>
  );
}
