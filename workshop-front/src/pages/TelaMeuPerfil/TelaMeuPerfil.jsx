import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaMeuPerfil.css';
import logo from '../../assets/ifba_logo.png';
import authSecurityService from '../../services/authSecurityService';
import eventRegistrationService from '../../services/eventRegistrationService';

export default function TelaMeuPerfil({ onVoltar }) {
  const [usuario, setUsuario] = useState(authSecurityService.getUser());
  const [eventosInscritos, setEventosInscritos] = useState(eventRegistrationService.getRegisteredEvents());

  const getUserRoleDisplay = (userRole) => {
    const roleMap = {
      'STUDENT': 'Estudante',
      'EMPLOYEE': 'Funcionário',
      'TEACHER': 'Professor',
      'VISITOR': 'Visitante'
    };
    return roleMap[userRole] || userRole;
  };

  const getAccessLevelDisplay = (accessLevel) => {
    const levelMap = {
      'ADMIN': 'Administrador',
      'USER': 'Usuário'
    };
    return levelMap[accessLevel] || accessLevel;
  };

  return (
    <div className="tela-container">
      <div className="principal-container meu-perfil">
        <div className="topo-pagina meu-perfil">
          <img 
            src={logo}
            alt="Logo" 
            className="logo tela-principal"
          />
          <h1 id="h1-meu-perfil">Meu Perfil</h1>
          <button className="secondary-btn btn-voltar meu-perfil" onClick={onVoltar}>Voltar</button>
        </div>

        <div className="perfil-container">
          <div className="perfil-info">
            <h2>Informações Pessoais</h2>
            <div className="info-group">
              <label>Nome:</label>
              <span>{usuario?.name || 'Não informado'}</span>
            </div>
            <div className="info-group">
              <label>Email:</label>
              <span>{usuario?.email || 'Não informado'}</span>
            </div>
            <div className="info-group">
              <label>Tipo de Usuário:</label>
              <span>{getUserRoleDisplay(usuario?.userRole)}</span>
            </div>
            <div className="info-group">
              <label>Nível de Acesso:</label>
              <span>{getAccessLevelDisplay(usuario?.accessLevel)}</span>
            </div>
            {usuario?.lastAccess && (
              <div className="info-group">
                <label>Último Acesso:</label>
                <span>{new Date(usuario.lastAccess).toLocaleString('pt-BR')}</span>
              </div>
            )}
          </div>

          <div className="estatisticas">
            <h2>Estatísticas</h2>
            <div className="stats-grid">
              <div className="stat-item">
                <h3>{eventosInscritos.length}</h3>
                <p>Eventos Inscritos</p>
              </div>
              <div className="stat-item">
                <h3>0</h3>
                <p>Certificados</p>
              </div>
              <div className="stat-item">
                <h3>{usuario?.createdAt ? Math.floor((new Date() - new Date(usuario.createdAt)) / (1000 * 60 * 60 * 24)) : 0}</h3>
                <p>Dias no Sistema</p>
              </div>
            </div>
          </div>

          {eventosInscritos.length > 0 && (
            <div className="eventos-recentes">
              <h2>Eventos Inscritos Recentes</h2>
              <div className="eventos-lista">
                {eventosInscritos.slice(0, 3).map((registration) => (
                  <div key={registration.eventId} className="evento-item">
                    <h4>{registration.event.titulo}</h4>
                    <p>{registration.event.categoria}</p>
                    <small>Inscrito em: {new Date(registration.registeredAt).toLocaleDateString('pt-BR')}</small>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}