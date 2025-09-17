import React, { useState } from 'react';
import './TelaPerfil.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaPerfil({ usuario, onVoltar }) {
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    name: usuario?.name || '',
    email: usuario?.email || '',
    phone: usuario?.phone || '',
    biography: usuario?.biography || ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSave = (e) => {
    e.preventDefault();
    // Here would be the API call to update profile
    alert('Perfil atualizado com sucesso!');
    setEditMode(false);
  };

  const handleCancel = () => {
    setFormData({
      name: usuario?.name || '',
      email: usuario?.email || '',
      phone: usuario?.phone || '',
      biography: usuario?.biography || ''
    });
    setEditMode(false);
  };

  const getUserRole = () => {
    if (!usuario) return 'Visitante';
    
    const accessLevel = typeof usuario.accessLevel === 'string' 
      ? usuario.accessLevel 
      : (usuario.accessLevel?.name || usuario.accessLevel);
    
    if (accessLevel === 'ADMIN') return 'Administrador';
    
    const userRole = typeof usuario.userRole === 'string' 
      ? usuario.userRole 
      : (usuario.userRole?.name || usuario.userRole);
    
    return userRole === 'STUDENT' ? 'Estudante' : 'Usuário';
  };

  return (
    <div className="tela-container">
      <div className="principal-container">
        <div className="topo-pagina">
          <img 
            src={logo}
            alt="Logo" 
            className="logo tela-principal"
          />
          <h1>Meu Perfil</h1>
        </div>

        <div className="perfil-container">
          <div className="perfil-card">
            <div className="perfil-header">
              <div className="perfil-avatar">
                <div className="avatar-placeholder">
                  {formData.name ? formData.name.charAt(0).toUpperCase() : 'U'}
                </div>
              </div>
              <div className="perfil-info">
                <h2>{formData.name || 'Usuário'}</h2>
                <p className="perfil-role">{getUserRole()}</p>
              </div>
              {!editMode && (
                <button 
                  className="edit-btn"
                  onClick={() => setEditMode(true)}
                >
                  Editar Perfil
                </button>
              )}
            </div>

            {editMode ? (
              <form onSubmit={handleSave} className="perfil-form">
                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="name">Nome Completo</label>
                    <input
                      type="text"
                      id="name"
                      name="name"
                      value={formData.name}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="email">E-mail</label>
                    <input
                      type="email"
                      id="email"
                      name="email"
                      value={formData.email}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="phone">Telefone</label>
                  <input
                    type="tel"
                    id="phone"
                    name="phone"
                    value={formData.phone}
                    onChange={handleInputChange}
                    placeholder="(00) 00000-0000"
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="biography">Biografia</label>
                  <textarea
                    id="biography"
                    name="biography"
                    value={formData.biography}
                    onChange={handleInputChange}
                    placeholder="Conte um pouco sobre você..."
                    rows="4"
                  />
                </div>

                <div className="form-buttons">
                  <button type="submit" className="primary-btn">Salvar</button>
                  <button type="button" className="secondary-btn" onClick={handleCancel}>
                    Cancelar
                  </button>
                </div>
              </form>
            ) : (
              <div className="perfil-details">
                <div className="detail-group">
                  <label>E-mail:</label>
                  <p>{formData.email || 'Não informado'}</p>
                </div>
                <div className="detail-group">
                  <label>Telefone:</label>
                  <p>{formData.phone || 'Não informado'}</p>
                </div>
                <div className="detail-group">
                  <label>Biografia:</label>
                  <p>{formData.biography || 'Nenhuma biografia adicionada.'}</p>
                </div>
              </div>
            )}
          </div>

          <button className="secondary-btn voltar-btn" onClick={onVoltar}>
            Voltar
          </button>
        </div>
      </div>
    </div>
  );
}