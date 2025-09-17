import React, { useState, useEffect } from 'react';
import './EventosInscritos.css';
import logo from '../../assets/ifba_logo.png';
import eventosMock from '../../mocks/eventosMock';
import eventRegistrationService from '../../services/eventRegistrationService';

export default function EventosInscritos({ usuario, onBack, onVisualizarMeusCertificados }) {
  const [eventosInscritos, setEventosInscritos] = useState([]);

  useEffect(() => {
    if (usuario) {
      const userId = usuario.id || usuario.email;
      const registeredEvents = eventRegistrationService.getRegisteredEventsWithData(userId, eventosMock);
      setEventosInscritos(registeredEvents);
    }
  }, [usuario]);

  const handleCancelRegistration = (eventId, eventTitle) => {
    if (window.confirm(`Deseja realmente cancelar a inscrição no evento "${eventTitle}"?`)) {
      const userId = usuario.id || usuario.email;
      const result = eventRegistrationService.unregisterFromEvent(userId, eventId);
      
      if (result.success) {
        alert(result.message);
        // Remove the event from the list
        setEventosInscritos(prev => prev.filter(evento => evento.eventId !== eventId));
      } else {
        alert(`Erro ao cancelar inscrição: ${result.message}`);
      }
    }
  };

  return (
    <div className="tela-container">
      <div className="principal-container">
        <div className="topo-pagina">
          <img 
            src={logo}
            alt="Logo" 
            className="logo tela-principal"
          />

          <div className="lista-botoes tela-principal">
            <button className="principal-btn tela-principal">Eventos Inscritos</button>
            <button className="principal-btn tela-principal" onClick={onVisualizarMeusCertificados}>
              Visualizar Certificados
            </button>
            <button className="principal-btn tela-principal">Ranking de Eventos</button>
          </div>
        </div>

        <h2 id="h2-eventos">Meus Eventos</h2>
        
        <div className="eventos-quadro">
          {eventosInscritos.length === 0 ? (
            <p>Você não está inscrito em nenhum evento.</p>
          ) : (
            eventosInscritos.map((evento) => (
              <div key={evento.eventId} className="quadro-evento">
                <h3>{evento.eventData.titulo}</h3>
                <p><strong>Descrição:</strong> {evento.eventData.descricao}</p>
                <p><strong>Categoria:</strong> {evento.eventData.categoria}</p>
                <p><strong>Data:</strong> {evento.eventData.dataInicio} até {evento.eventData.dataFim}</p>
                <p><strong>Palestrante:</strong> {evento.eventData.palestrante}</p>
                <p><strong>Localidade:</strong> {evento.eventData.localidade}</p>
                {evento.eventData.localidade === 'Remota' && (
                  <p><strong>Link:</strong> <a href={evento.eventData.link} target="_blank" rel="noopener noreferrer">{evento.eventData.link}</a></p>
                )}
                {evento.eventData.localidade === 'Presencial' && (
                  <p><strong>Sala:</strong> {evento.eventData.sala}</p>
                )}
                <p><strong>Data de Inscrição:</strong> {new Date(evento.registrationDate).toLocaleDateString('pt-BR')}</p>
                
                <div className="evento-actions">
                  <button 
                    className="btn-cancelar"
                    onClick={() => handleCancelRegistration(evento.eventId, evento.eventData.titulo)}
                  >
                    Cancelar Inscrição
                  </button>
                </div>
              </div>
            ))
          )}
        </div>

        <button className="secondary-btn eventos-inscritos" onClick={onBack}>Voltar</button>
      </div>
    </div>
  );
}