import React, { useState } from 'react';
import './TelaPrincipal.css';
import logo from '../../assets/ifba_logo.png';
import { FaBars, FaBell, FaClipboardList, FaCertificate, FaTrophy } from 'react-icons/fa';
import eventosMock from '../../mocks/eventosMock'; 
import InscricaoEventoModal from '../../components/InscricaoEventoModal/InscricaoEventoModal';
import { fetchUnreadCount } from '../../services/notificationService';
import eventRegistrationService from '../../services/eventRegistrationService';
import { useEffect } from 'react';

export default function TelaPrincipal({ usuario, onLogout, onCadastrarEvento, onAbrirAdministrador, onVisualizarMeusEventos, onVisualizarMeusCertificados }) {
  const [menuAberto, setMenuAberto] = useState(false);
  const [modalAberto, setModalAberto] = useState(false);
  const [eventoSelecionado, setEventoSelecionado] = useState(null);
  const [unread, setUnread] = useState(0);
  const [registeredEvents, setRegisteredEvents] = useState([]);

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
    
    // Load registered events
    const registered = eventRegistrationService.getRegisteredEvents();
    setRegisteredEvents(registered);
    
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

  const eventosAprovados = eventosMock.filter(evento => evento.aprovado === true);

  const abrirModalInscricao = (evento) => {
    setEventoSelecionado(evento);
    setModalAberto(true);
  };

  const confirmarInscricao = (evento) => {
    setModalAberto(false);
    eventRegistrationService.registerForEvent(evento);
    setRegisteredEvents(eventRegistrationService.getRegisteredEvents());
    alert(`Inscrição confirmada no evento: ${evento.titulo}`);
    // acrescentar lógica para salvar inscrição por API posteriormente
  };

  const handleButtonClick = (evento) => {
    const isRegistered = eventRegistrationService.isRegisteredForEvent(evento.id);
    if (isRegistered) {
      alert('Você já está inscrito neste evento!');
    } else {
      abrirModalInscricao(evento);
    }
  };

  // Get user role for permission checks
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
              <button className="principal-btn tela-principal btn-icone" onClick={() => window.dispatchEvent(new CustomEvent('openNotifications'))}>
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

        <h2 id="h2-eventos">Eventos Disponíveis</h2>

        <div className="eventos-quadro">
          {eventosAprovados.length === 0 ? (
            <p>Nenhum evento disponível.</p>
          ) : (
            eventosAprovados.map((evento) => {
              const isRegistered = eventRegistrationService.isRegisteredForEvent(evento.id);
              return (
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
                    className={`btn-inscrever ${isRegistered ? 'inscrito' : ''}`}
                    onClick={() => handleButtonClick(evento)}
                  >
                    {isRegistered ? 'Inscrito' : 'Inscrever-se'}
                  </button>

                </div>
              );
            })
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