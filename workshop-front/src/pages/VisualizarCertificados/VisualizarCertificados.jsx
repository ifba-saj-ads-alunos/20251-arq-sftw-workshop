import React from 'react';
import './VisualizarCertificados.css';
import logo from '../../assets/ifba_logo.png';

export default function VisualizarCertificados({onBack, onVisualizarMeusEventos}) {
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
            <button className="principal-btn tela-principal" onClick={onVisualizarMeusEventos}>
                Eventos Inscritos
            </button>
            <button className="principal-btn tela-principal">Visualizar Certificados</button>
            <button className="principal-btn tela-principal">Ranking de Eventos</button>
          </div>
        </div>

        <h2 id="h2-certificados">Meus Certificados</h2>
        
        <div className="certificados-quadro">
          <p>Nenhum certificado disponível.</p>
        </div>

        <button className="secondary-btn eventos-inscritos" onClick={onBack}>Voltar</button>

      </div>
    </div>
  );
}