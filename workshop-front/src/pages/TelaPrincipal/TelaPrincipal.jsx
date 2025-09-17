import React, { useState } from 'react';
import './TelaPrincipal.css';
import logo from '../../assets/ifba_logo.png';
import { FaBars } from 'react-icons/fa';
import eventosMock from '../../mocks/eventosMock'; 
import InscricaoEventoModal from '../../components/InscricaoEventoModal/InscricaoEventoModal';
import { fetchUnreadCount } from '../../services/notificationService';
import { useEffect } from 'react';

export default function TelaPrincipal({ usuario, eventosInscritos, setEventosInscritos, onLogout, onCadastrarEvento, onAbrirAdministrador, onVisualizarMeusEventos, onVisualizarMeusCertificados, onAbrirSugestoes, onAbrirPerfil }) {
  const [menuAberto, setMenuAberto] = useState(false);
  const [modalAberto, setModalAberto] = useState(false);
  const [eventoSelecionado, setEventoSelecionado] = useState(null);
  const [unread, setUnread] = useState(0);

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

  const eventosAprovados = eventosMock.filter(evento => evento.aprovado === true);

  const abrirModalInscricao = (evento) => {
    setEventoSelecionado(evento);
    setModalAberto(true);
  };

  const confirmarInscricao = (evento) => {
    setModalAberto(false);
    // Add event to subscribed events
    setEventosInscritos(prev => [...prev, evento]);
    alert(`Inscrição confirmada no evento: ${evento.titulo}`);
    // acrescentar lógica para salvar inscrição por API posteriormente
  };

  const isEventoInscrito = (eventoId) => {
    return eventosInscritos.some(evento => evento.id === eventoId);
  };

  const handleClickInscricao = (evento) => {
    if (isEventoInscrito(evento.id)) {
      alert('Você já está inscrito neste evento!');
    } else {
      abrirModalInscricao(evento);
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
            {/* Only allow authenticated users with proper roles to access event creation */}
            {usuario && usuario.accessLevel === 'ADMIN' && (
              <button onClick={onCadastrarEvento}>Cadastrar Evento</button>
            )}
            {/* Only allow admin users to access administrator panel */}
            {usuario && usuario.accessLevel === 'ADMIN' && (
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
          {eventosAprovados.length === 0 ? (
            <p>Nenhum evento disponível.</p>
          ) : (
            eventosAprovados.map((evento) => (
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
                  className={`btn-inscrever ${isEventoInscrito(evento.id) ? 'inscrito' : ''}`}
                  onClick={() => handleClickInscricao(evento)}
                >
                  {isEventoInscrito(evento.id) ? 'Inscrito' : 'Inscrever-se'}
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