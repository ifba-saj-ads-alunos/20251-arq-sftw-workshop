import React, { useState } from 'react';
import '../styles/TelaPrincipal.css';
import logo from '../assets/ifba_logo.png';
import { FaBars } from 'react-icons/fa';

export default function TelaPrincipal({ onLogout, onCadastrarEvento, onAbrirAdministrador }) {
  const [menuAberto, setMenuAberto] = useState(false);

  const toggleMenu = () => {
    setMenuAberto(!menuAberto);
  };

  return (
    <div className="tela-container">

      <div className="principal-container">

        <div className="menu-hamburguer" onClick={toggleMenu}>
          <FaBars size={24} />
        </div>
        
        {menuAberto && <div className="overlay" onClick={() => setMenuAberto(false)} />}
          
        {menuAberto && (
          <div className="menu-lateral">
            <button onClick={() => alert('Meu Perfil')}>Meu Perfil</button>
            <button onClick={onCadastrarEvento}>Cadastrar Evento</button>
            <button onClick={() => {
              setMenuAberto(false);
              onAbrirAdministrador();
            }}>
              Administrador
            </button>
            <button onClick={() => alert('Sugestões')}>Sugestões</button>
            <button onClick={onLogout}>Logout</button>
          </div>
        )}

        <img 
          src={logo}
          alt="Logo" 
          className="logo"
        />

        <h2>Eventos Disponíveis</h2>
        
        <div className="eventos-quadro">
          <p>Nenhum evento disponível.</p>
        </div>

        <button className="principal-btn">Cursos Inscritos</button>
        <button className="principal-btn">Visualizar Certificados</button>
        <button className="principal-btn">Ranking de Eventos</button>
      </div>
    </div>
  );
}