import React from 'react';
import '../styles/TelaPrincipal.css';
import logo from '../assets/ifba_logo.png';

export default function TelaPrincipal({ onLogout, onCadastrarEvento }) {
  return (
    <div className="principal-container">
      <img 
        src={logo}
        alt="Logo" 
        className="logo"
      />

      <h2>Eventos Disponíveis</h2>

      <div className="eventos-quadro">
        {/* Aqui futuramente tera a lista de eventos dinamicamente */}
        <p>Evento 1 - Nome do Evento</p>
        <p>Evento 2 - Nome do Evento</p>
        <p>Evento 3 - Nome do Evento</p>
      </div>

      <button className="principal-btn">Cursos Inscritos</button>
      <button className="principal-btn" onClick={onCadastrarEvento}>Cadastrar Evento</button>
      <button className="principal-btn">Visualizar Certificados</button>
      <button className="logout-btn" onClick={onLogout}>Logout</button>
    </div>
  );
}
