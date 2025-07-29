import React, { useState } from 'react';
import './CadastroUsuario.css';
import logo from '../../assets/ifba_logo.png';
import userService from '../../services/userService';
import { useSnackbar } from '../../components/Snackbar/SnackbarContext';

export default function CadastroUsuario({ onBack }) {
  const { showSuccess, showError } = useSnackbar();
  
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    senha: '',
    tipo: 'Estudante'
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const mapTipoToUserRole = (tipo) => {
    const mapping = {
      'Estudante': 'STUDENT',
      'Funcionário': 'EMPLOYEE',
      'Professor': 'TEACHER',
      'Visitante': 'VISITOR'
    };
    return mapping[tipo] || 'STUDENT';
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    if (!formData.nome || !formData.email || !formData.senha) {
      showError('Por favor, preencha todos os campos obrigatórios.');
      setLoading(false);
      return;
    }

    if (formData.senha.length < 6) {
      showError('A senha deve ter pelo menos 6 caracteres.');
      setLoading(false);
      return;
    }

    try {
      const userData = {
        name: formData.nome,
        email: formData.email,
        password: formData.senha,
        userRole: mapTipoToUserRole(formData.tipo)
      };

      await userService.createUser(userData);
      showSuccess('Usuário cadastrado com sucesso!');
      
      setFormData({
        nome: '',
        email: '',
        senha: '',
        tipo: 'Estudante'
      });
      
      setTimeout(() => {
        onBack();
      }, 2000);
      
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        
        if (status === 400) {
          showError(data.message || 'Dados inválidos. Verifique os campos e tente novamente.');
        } else if (status === 409) {
          showError('Este email já está em uso. Tente com outro email.');
        } else {
          showError('Erro no servidor. Tente novamente mais tarde.');
        }
      } else if (error.request) {
        showError('Erro de conexão. Verifique sua internet e tente novamente.');
      } else {
        showError('Erro inesperado. Tente novamente.');
      }
    }

    setLoading(false);
  };

  return (
    <div className="cadastro-container">
      <img src={logo} alt="Logo" className="logo cadastro-usuario" />
      <form onSubmit={handleSubmit} className="cadastro-form">
        <label className='cadastro-usuario'>
          Nome:
          <input 
            type="text" 
            name="nome" 
            value={formData.nome} 
            onChange={handleChange} 
            className='cadastro-usuario'
            required
          />
        </label>

        <label className='cadastro-usuario'>
          Email:
          <input 
            type="email" 
            name="email" 
            value={formData.email} 
            onChange={handleChange} 
            className='cadastro-usuario'
            required
          />
        </label>

        <label className='cadastro-usuario'>
          Perfil:
          <select name="tipo" value={formData.tipo} onChange={handleChange} className='cadastro-usuario'>
            <option value="Estudante">Estudante</option>
            <option value="Funcionário">Funcionário</option>
            <option value="Professor">Professor</option>
            <option value="Visitante">Visitante</option>
          </select>
        </label>

        <label className='cadastro-usuario'>
          Senha:
          <input 
            type="password" 
            name="senha" 
            value={formData.senha} 
            onChange={handleChange} 
            className='cadastro-usuario'
            minLength="6"
            required
          />
        </label>

        <button type="submit" className="cadastro-btn cadastro-usuario" disabled={loading}>
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>

        <button type="button" className="secondary-btn cadastro-usuario" onClick={onBack}>
          Voltar
        </button>
      </form>
    </div>
  );
}
