import React, { useState } from 'react';
import './EventosInscritos.css';
import logo from '../../assets/ifba_logo.png';

export default function EventosInscritos({onBack, onVisualizarMeusCertificados}) {
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
          <p>Nenhum evento disponível.</p>
        </div>

        <button className="secondary-btn eventos-inscritos" onClick={onBack}>Voltar</button>

      </div>
    </div>
  );
}