import React, { useState, useEffect } from 'react';
import './EventosInscritos.css';
import logo from '../../assets/ifba_logo.png';
import { FaBars, FaBell, FaClipboardList, FaCertificate, FaTrophy } from 'react-icons/fa';
import eventRegistrationService from '../../services/eventRegistrationService';

export default function EventosInscritos({usuario, onBack, onVisualizarMeusCertificados, onLogout, onCadastrarEvento, onAbrirAdministrador, onVisualizarMeusEventos}) {
  const [eventosInscritos, setEventosInscritos] = useState([]);
  const [menuAberto, setMenuAberto] = useState(false);
  const [unread, setUnread] = useState(0);

  useEffect(() => {
    const registrations = eventRegistrationService.getRegisteredEvents();
    setEventosInscritos(registrations);
  }, []);

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
              <button className="principal-btn tela-principal btn-icone" onClick={() => window.dispatchEvent(new CustomEvent('openNotifications'))}>
                <span className="icone-btn"><FaBell /></span>
                <span className="texto-btn">Notificações</span>
              </button>
              {unread > 0 && (
                <span className="badge-unread">{unread}</span>
              )}
            </div>
            <button className="principal-btn tela-principal btn-icone selected-screen" onClick={onVisualizarMeusEventos}>
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

        <div className="eventos-inscritos-header">
          <h2 id="h2-eventos-inscritos">Meus Eventos</h2>
          <button className="secondary-btn btn-voltar eventos-inscritos" onClick={onBack}>Voltar</button>
        </div>
        
        <div className="eventos-quadro">
          {eventosInscritos.length === 0 ? (
            <p>Nenhum evento disponível.</p>
          ) : (
            eventosInscritos.map((registration) => {
              const evento = registration.event;
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
                  <p><strong>Inscrito em:</strong> {new Date(registration.registeredAt).toLocaleDateString('pt-BR')}</p>
                </div>
              );
            })
          )}
        </div>
      </div>
    </div>
  );
}