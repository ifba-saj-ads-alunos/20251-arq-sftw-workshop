import React from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaAdministrador({ onVoltar, onAprovarEvento }) {
  return (
    <div className="principal-container">
      <img 
        src={logo}
        alt="Logo" 
        className="logo"
      />

      <button className="principal-btn" onClick={onAprovarEvento}>Aprovar Evento</button>
      <button className="principal-btn">Excluir Evento</button>
      <button className="principal-btn">Cadastrar Sala</button>
      <button className="principal-btn">Editar Sala</button>
      <button className="principal-btn">Cadastrar Admin</button>
      <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
    </div>
  );
}
