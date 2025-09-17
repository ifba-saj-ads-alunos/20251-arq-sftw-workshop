import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaPerfil.css';

export default function TelaPerfil({ usuario, onVoltar }) {
  const [editando, setEditando] = useState(false);
  const [dadosEditados, setDadosEditados] = useState({
    name: usuario?.name || '',
    email: usuario?.email || '',
    cpf: usuario?.cpf || ''
  });

  const getUserRole = () => {
    if (!usuario) return 'N/A';
    const role = typeof usuario.userRole === 'string' ? usuario.userRole : (usuario.userRole?.name || usuario.userRole);
    const translations = {
      'STUDENT': 'Estudante',
      'TEACHER': 'Professor',
      'EMPLOYEE': 'Funcionário',
      'VISITOR': 'Visitante'
    };
    return translations[role] || role;
  };

  const getAccessLevel = () => {
    if (!usuario) return 'N/A';
    const accessLevel = typeof usuario.accessLevel === 'string' ? usuario.accessLevel : (usuario.accessLevel?.name || usuario.accessLevel);
    const translations = {
      'ADMIN': 'Administrador',
      'USER': 'Usuário'
    };
    return translations[accessLevel] || accessLevel;
  };

  const handleSave = () => {
    // TODO: Implement API call to update user data
    console.log('Dados atualizados:', dadosEditados);
    alert('Dados atualizados com sucesso!');
    setEditando(false);
  };

  const handleCancel = () => {
    setDadosEditados({
      name: usuario?.name || '',
      email: usuario?.email || '',
      cpf: usuario?.cpf || ''
    });
    setEditando(false);
  };

  return (
    <div className="principal-container">
      <h2>Meu Perfil</h2>
      
      <div className="perfil-container">
        <div className="perfil-section">
          <h3>Informações Pessoais</h3>
          
          <div className="perfil-field">
            <label>Nome:</label>
            {editando ? (
              <input
                type="text"
                value={dadosEditados.name}
                onChange={(e) => setDadosEditados({...dadosEditados, name: e.target.value})}
              />
            ) : (
              <span>{usuario?.name || 'N/A'}</span>
            )}
          </div>

          <div className="perfil-field">
            <label>Email:</label>
            {editando ? (
              <input
                type="email"
                value={dadosEditados.email}
                onChange={(e) => setDadosEditados({...dadosEditados, email: e.target.value})}
              />
            ) : (
              <span>{usuario?.email || 'N/A'}</span>
            )}
          </div>

          <div className="perfil-field">
            <label>CPF:</label>
            {editando ? (
              <input
                type="text"
                value={dadosEditados.cpf}
                onChange={(e) => setDadosEditados({...dadosEditados, cpf: e.target.value})}
              />
            ) : (
              <span>{usuario?.cpf || 'N/A'}</span>
            )}
          </div>
        </div>

        <div className="perfil-section">
          <h3>Informações do Sistema</h3>
          
          <div className="perfil-field">
            <label>Perfil:</label>
            <span>{getUserRole()}</span>
          </div>

          <div className="perfil-field">
            <label>Nível de Acesso:</label>
            <span>{getAccessLevel()}</span>
          </div>

          <div className="perfil-field">
            <label>Data de Cadastro:</label>
            <span>{usuario?.createdAt ? new Date(usuario.createdAt).toLocaleDateString('pt-BR') : 'N/A'}</span>
          </div>
        </div>

        <div className="perfil-actions">
          {editando ? (
            <>
              <button className="primary-btn" onClick={handleSave}>Salvar</button>
              <button className="secondary-btn" onClick={handleCancel}>Cancelar</button>
            </>
          ) : (
            <button className="primary-btn" onClick={() => setEditando(true)}>Editar Perfil</button>
          )}
          <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
        </div>
      </div>
    </div>
  );
}