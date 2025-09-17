import React, { useState, useEffect } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaAprovarEvento.css';
import logo from '../../assets/ifba_logo.png';
import EventoParaAprovar from '../../components/EventoParaAprovar';
import { approveEvent, rejectEvent } from '../../services/eventService';
import { fetchPendingEvents } from '../../services/eventService';

export default function TelaAprovarEvento({ onVoltar }) {
  const [eventosParaAprovar, setEventosParaAprovar] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let mounted = true;
    (async () => {
      try {
        const list = await fetchPendingEvents();
        // Map backend EventOutput to UI shape expected by EventoParaAprovar
        if (!mounted) return;
        const mapped = (list || []).map(e => ({
          id: e.id,
          titulo: e.title,
          descricao: e.description,
          aprovado: null,
          justificativa: '',
          raw: e
        }));
        setEventosParaAprovar(mapped);
      } catch (err) {
        console.error('Erro ao buscar eventos pendentes', err);
      } finally {
        if (mounted) setLoading(false);
      }
    })();
    return () => { mounted = false; };
  }, []);

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

  const handleEnviar = async (evento) => {
    if (evento.aprovado === false && !evento.justificativa.trim()) {
      alert('Por favor, adicione uma justificativa para reprovar o evento.');
      return;
    }

    try {
      if (evento.aprovado) {
        await approveEvent(evento.id);
      } else {
        await rejectEvent(evento.id, evento.justificativa);
      }
      alert(`Evento ${evento.aprovado ? 'aprovado' : 'reprovado'} com sucesso!`);
      setEventosParaAprovar(prev => prev.filter(e => e.id !== evento.id));
    } catch (err) {
      console.error(err);
      alert('Erro ao enviar decisão para o servidor.');
    }
  };

  return (
    <div className="principal-container">
      <img src={logo} alt="Logo" className="logo" />
      <div className='eventos'>
        {loading ? (
          <p>Carregando eventos...</p>
        ) : eventosParaAprovar.length === 0 ? (
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
