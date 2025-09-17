import React from 'react';
import './EventosInscritos.css';
import logo from '../../assets/ifba_logo.png';

export default function EventosInscritos({eventosInscritos, onBack, onVisualizarMeusCertificados}) {
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
          {eventosInscritos && eventosInscritos.length === 0 ? (
            <p>Nenhum evento inscrito.</p>
          ) : (
            eventosInscritos?.map((evento) => (
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
                <div className="status-inscricao">
                  <span className="inscrito-badge">✓ Inscrito</span>
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