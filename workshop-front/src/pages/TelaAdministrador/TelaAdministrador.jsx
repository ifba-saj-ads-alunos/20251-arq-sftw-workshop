import React from 'react';
import './TelaAdministrador.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaAdministrador({ onVoltar, onAprovarEvento }) {
  return (
    <div className="admin-container">
      <img 
        src={logo}
        alt="Logo" 
        className="logo"
      />

      <h2>Opções do Administrador</h2>

      <button className="principal-btn" onClick={onAprovarEvento}>Aprovar Evento</button>
      <button className="principal-btn">Excluir Evento</button>
      <button className="principal-btn">Cadastrar Sala</button>
      <button className="principal-btn">Editar Sala</button>
      <button className="principal-btn">Gerenciar Usuarios</button>
      <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
    </div>
  );
}
