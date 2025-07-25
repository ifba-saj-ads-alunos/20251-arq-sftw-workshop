import React from 'react';

export default function EventoParaAprovar({
  evento,
  onAprovar,
  onReprovar,
  onJustificativaChange,
  onEnviar
}) {
  return (
    <div className="quadro-evento">
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
        <button className="aprovar-btn" onClick={() => onAprovar(evento.id)}>✔️</button>
        <button className="reprovar-btn" onClick={() => onReprovar(evento.id)}>❌</button>
      </div>

      {evento.aprovado === false && (
        <textarea
          placeholder="Justificativa da reprovação"
          value={evento.justificativa}
          onChange={(e) => onJustificativaChange(evento.id, e.target.value)}
          style={{ width: '100%', marginBottom: '0.5rem' }}
        />
      )}

      {evento.aprovado !== null && (
        <button
          className="principal-btn tela-aprovar-evento"
          onClick={() => onEnviar(evento)}
        >
          Enviar
        </button>
      )}
    </div>
  );
}
