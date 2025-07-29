import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaAprovarEvento.css';
import logo from '../../assets/ifba_logo.png';
import eventosMock from '../../mocks/eventosMock';
import EventoParaAprovar from '../../components/EventoParaAprovar';

export default function TelaAprovarEvento({ onVoltar }) {
  const [eventosParaAprovar, setEventosParaAprovar] = useState(eventosMock);

  const handleAprovar = (id) => {
    setEventosParaAprovar(prev =>
      prev.map(evento =>
        evento.id === id ? { ...evento, aprovado: true, justificativa: '' } : evento
      )
    );
  };

  const handleReprovar = (id) => {
    setEventosParaAprovar(prev =>
      prev.map(evento =>
        evento.id === id ? { ...evento, aprovado: false } : evento
      )
    );
  };

  const handleJustificativaChange = (id, texto) => {
    setEventosParaAprovar(prev =>
      prev.map(evento =>
        evento.id === id ? { ...evento, justificativa: texto } : evento
      )
    );
  };

  const handleEnviar = (evento) => {
    if (evento.aprovado === false && !evento.justificativa.trim()) {
      alert('Por favor, adicione uma justificativa para reprovar o evento.');
      return;
    }

    alert(`Evento ${evento.aprovado ? 'aprovado' : 'reprovado'} com sucesso!`);

    setEventosParaAprovar(prev => prev.filter(e => e.id !== evento.id));
  };

  return (
    <div className="principal-container">
      <img src={logo} alt="Logo" className="logo" />
      <div className='eventos'>
        {eventosParaAprovar.length === 0 ? (
          <p>Nenhum evento para aprovação.</p>
        ) : (
          eventosParaAprovar
            .filter((evento) => evento.aprovado === null)
            .map((evento) => (
            <EventoParaAprovar
              key={evento.id}
              evento={evento}
              onAprovar={handleAprovar}
              onReprovar={handleReprovar}
              onJustificativaChange={handleJustificativaChange}
              onEnviar={handleEnviar}
            />
          ))
        )}
      </div>
      <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
    </div>
  );
}
