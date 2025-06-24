import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaAprovarEvento.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaAprovarEvento({ onVoltar }) {
  const [eventosParaAprovar, setEventosParaAprovar] = useState([ //apenas para teste
    {
      id: 1,
      titulo: 'Workshop de React',
      descricao: 'Aprenda os fundamentos do React com exemplos práticos.',
      categoria: 'Minicurso',
      dataInicio: '2025-06-20',
      dataFim: '2025-06-21',
      vagas: 30,
      palestrante: 'Alex Gondim Lima',
      curriculo: 'Professor de Arquitetura de Software.',
      localidade: 'Remota',
      link: 'https://meet.google.com/workshop-react',
      sala: '',
      aprovado: null, // null = não decidido, true = aprovado, false = reprovado
      justificativa: '',
    },
    {
      id: 2,
      titulo: 'Palestra sobre IA',
      descricao: 'Descubra como a Inteligência Artificial está mudando o mundo.',
      categoria: 'Palestra',
      dataInicio: '2025-06-25',
      dataFim: '2025-06-25',
      vagas: 50,
      palestrante: 'Maria Santos',
      curriculo: 'Pesquisadora em IA no IFBA.',
      localidade: 'Presencial',
      link: '',
      sala: 'Auditório 1',
      aprovado: null,
      justificativa: '',
    },
  ]);

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

    console.log('Evento enviado para processamento:', evento);
    alert(`Evento ${evento.aprovado ? 'aprovado' : 'reprovado'} com sucesso!`);

    // Remover da lista
    setEventosParaAprovar(prev => prev.filter(e => e.id !== evento.id));
  };

  return (
    <div className="principal-container">
      <img src={logo} alt="Logo" className="logo" />
      <h2>Aprovação de Eventos</h2>

      {eventosParaAprovar.length === 0 ? (
        <p>Nenhum evento para aprovação.</p>
      ) : (
        eventosParaAprovar.map((evento) => (
          <div key={evento.id} className="eventos-quadro">
            <h3>{evento.titulo}</h3>
            <p><strong>Descrição:</strong> {evento.descricao}</p>
            <p><strong>Categoria:</strong> {evento.categoria}</p>
            <p><strong>Data:</strong> {evento.dataInicio} até {evento.dataFim}</p>
            <p><strong>Vagas:</strong> {evento.vagas}</p>
            <p><strong>Palestrante:</strong> {evento.palestrante}</p>
            <p><strong>Currículo:</strong> {evento.curriculo}</p>
            <p><strong>Localidade:</strong> {evento.localidade}</p>
            {evento.localidade === 'Remota' && (
              <p><strong>Link:</strong> <a href={evento.link} target="_blank" rel="noopener noreferrer">{evento.link}</a></p>
            )}
            {evento.localidade === 'Presencial' && (
              <p><strong>Sala:</strong> {evento.sala}</p>
            )}

            <div style={{ display: 'flex', gap: '1rem', margin: '0.5rem 0' }}>
              <button className="aprovar-btn" onClick={() => handleAprovar(evento.id)}>✔️</button>
              <button className="reprovar-btn" onClick={() => handleReprovar(evento.id)}>❌</button>
            </div>

            {evento.aprovado === false && (
              <textarea
                placeholder="Justificativa da reprovação"
                value={evento.justificativa}
                onChange={(e) => handleJustificativaChange(evento.id, e.target.value)}
                style={{ width: '100%', marginBottom: '0.5rem' }}
              />
            )}

            {evento.aprovado !== null && (
              <button
                className="principal-btn"
                onClick={() => handleEnviar(evento)}
              >
                Enviar
              </button>
            )}
          </div>
        ))
      )}

      <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
    </div>
  );
}
