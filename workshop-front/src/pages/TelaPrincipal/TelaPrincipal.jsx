import React, { useState } from 'react';
import './TelaPrincipal.css';
import logo from '../../assets/ifba_logo.png';
import { FaBars } from 'react-icons/fa';
import eventosMock from '../../mocks/eventosMock'; 
import InscricaoEventoModal from '../../components/InscricaoEventoModal/InscricaoEventoModal';
import { fetchUnreadCount } from '../../services/notificationService';
import eventRegistrationService from '../../services/eventRegistrationService';
import { useEffect } from 'react';

export default function TelaPrincipal({ usuario, onLogout, onCadastrarEvento, onAbrirAdministrador, onVisualizarMeusEventos, onVisualizarMeusCertificados, onAbrirSugestoes, onAbrirPerfil }) {
  const [menuAberto, setMenuAberto] = useState(false);
  const [modalAberto, setModalAberto] = useState(false);
  const [eventoSelecionado, setEventoSelecionado] = useState(null);
  const [unread, setUnread] = useState(0);
  const [eventosDisponiveis, setEventosDisponiveis] = useState([]);

  // Update available events based on user registrations
  useEffect(() => {
    if (usuario) {
      const userId = usuario.id || usuario.email; // Use ID or email as user identifier
      const eventosAprovados = eventosMock.filter(evento => evento.aprovado === true);
      
      // Filter out events the user is already registered for
      const eventosNaoInscritos = eventosAprovados.filter(evento => 
        !eventRegistrationService.isRegisteredForEvent(userId, evento.id)
      );
      
      setEventosDisponiveis(eventosNaoInscritos);
    }
  }, [usuario]);

  useEffect(() => {
    let mounted = true;
    (async () => {
      try {
        const n = await fetchUnreadCount();
        if (mounted) setUnread(n);
      } catch (err) {
        console.error('Erro ao buscar unread count', err);
      }
    })();
    return () => { mounted = false; };
  }, []);

  useEffect(() => {
    const handler = async () => {
      try {
        const n = await fetchUnreadCount();
        setUnread(n);
      } catch (err) { console.error('Erro ao atualizar unread count', err); }
    };
    window.addEventListener('notificationsUpdated', handler);
    return () => window.removeEventListener('notificationsUpdated', handler);
  }, []);

  const toggleMenu = () => setMenuAberto(!menuAberto);

  // Helper functions to check user permissions
  const getUserRole = () => {
    if (!usuario) return null;
    return typeof usuario.userRole === 'string' ? usuario.userRole : (usuario.userRole?.name || usuario.userRole);
  };

  const getAccessLevel = () => {
    if (!usuario) return null;
    return typeof usuario.accessLevel === 'string' ? usuario.accessLevel : (usuario.accessLevel?.name || usuario.accessLevel);
  };

  const canCreateEvents = () => {
    const role = getUserRole();
    const accessLevel = getAccessLevel();
    // Only non-visitors with appropriate permissions can create events
    return role !== 'VISITOR' && (accessLevel === 'ADMIN' || role === 'TEACHER' || role === 'EMPLOYEE');
  };

  const canAccessAdmin = () => {
    const accessLevel = getAccessLevel();
    // Only users with ADMIN access level can access admin panel
    return accessLevel === 'ADMIN';
  };

  const abrirModalInscricao = (evento) => {
    setEventoSelecionado(evento);
    setModalAberto(true);
  };

  const confirmarInscricao = (evento) => {
    setModalAberto(false);
    
    if (usuario) {
      const userId = usuario.id || usuario.email;
      const result = eventRegistrationService.registerForEvent(userId, evento);
      
      if (result.success) {
        alert(`Inscrição confirmada no evento: ${evento.titulo}`);
        
        // Update available events by removing the registered event
        setEventosDisponiveis(prev => prev.filter(e => e.id !== evento.id));
      } else {
        alert(`Erro na inscrição: ${result.message}`);
      }
    } else {
      alert('Erro: usuário não identificado');
    }
  };

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
              onAbrirPerfil();
            }}>Meu Perfil</button>
            {canCreateEvents() && (
              <button onClick={onCadastrarEvento}>Cadastrar Evento</button>
            )}
            {canAccessAdmin() && (
              <button onClick={() => {
                setMenuAberto(false);
                onAbrirAdministrador();
              }}>
                Administrador
              </button>
            )}
            <button onClick={() => {
              setMenuAberto(false);
              onAbrirSugestoes();
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
            <div style={{position: 'relative', display: 'inline-block', marginRight: '8px'}}>
              <button className="principal-btn tela-principal" onClick={() => window.dispatchEvent(new CustomEvent('openNotifications'))}>
                Notificações
              </button>
              {unread > 0 && (
                <span className="badge-unread">{unread}</span>
              )}
            </div>
            <button className="principal-btn tela-principal" onClick={onVisualizarMeusEventos}>
              Eventos Inscritos
            </button>
            <button className="principal-btn tela-principal" onClick={onVisualizarMeusCertificados}>
              Certificados
            </button>
            <button className="principal-btn tela-principal">Ranking de Eventos</button>
          </div>
        </div>

        <h2 id="h2-eventos">Eventos Disponíveis</h2>

        <div className="eventos-quadro">
          {eventosDisponiveis.length === 0 ? (
            <p>Nenhum evento disponível.</p>
          ) : (
            eventosDisponiveis.map((evento) => (
              <div key={evento.id} className="quadro-evento">
                <h3>{evento.titulo}</h3>
                <p><strong>Descrição:</strong> {evento.descricao}</p>
                <p><strong>Categoria:</strong> {evento.categoria}</p>
                <p><strong>Data:</strong> {evento.dataInicio} até {evento.dataFim}</p>
                <p><strong>Vagas:</strong> {evento.vagas}</p>
                <p><strong>Palestrante:</strong> {evento.palestrante}</p>
                <p><strong>Localidade:</strong> {evento.localidade}</p>
                {evento.localidade === 'Remota' && (
                  <p><strong>Link:</strong> <a href={evento.link} target="_blank" rel="noopener noreferrer">{evento.link}</a></p>
                )}
                {evento.localidade === 'Presencial' && (
                  <p><strong>Sala:</strong> {evento.sala}</p>
                )}

                <button 
                  className="btn-inscrever"
                  onClick={() => abrirModalInscricao(evento)}
                >
                  Inscrever-se
                </button>

              </div>
            ))
          )}
        </div>
      </div>

       {modalAberto && eventoSelecionado && (
        <InscricaoEventoModal
          evento={eventoSelecionado}
          onConfirmar={confirmarInscricao}
          onCancelar={() => setModalAberto(false)}
        />
      )}
    </div>
  );
}