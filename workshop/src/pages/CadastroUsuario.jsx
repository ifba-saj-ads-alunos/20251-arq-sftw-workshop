import React, { useState } from 'react';
import '../CadastroUsuario.css';
import logo from '../assets/ifba_logo.png';

export default function CadastroUsuario({ onBack }) {
  const [formData, setFormData] = useState({
    nome: '',
    cpf: '',
    email: '',
    tipo: 'Discente',
    senha: ''
  });

  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus('');

    // Simular envio (sem backend)
    setTimeout(() => {
      if (formData.nome && formData.cpf && formData.email && formData.senha) {
        setStatus('Usuário cadastrado com sucesso!');
      } else {
        setStatus('Erro: preencha todos os campos corretamente.');
      }
      setLoading(false);
    }, 1000); // 1 segundo de "espera"
  };

  return (
    <div className="cadastro-container">
      <img 
        src={logo}
        alt="Logo" 
        className="logo"
      />
      <form onSubmit={handleSubmit} className="cadastro-form">
        <label>
          Nome:
          <input
            type="text"
            name="nome"
            value={formData.nome}
            onChange={handleChange}
          />
        </label>

        <label>
          CPF:
          <input
            type="text"
            name="cpf"
            value={formData.cpf}
            onChange={handleChange}
          />
        </label>

        <label>
          Email:
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </label>

        <label>
          Tipo:
          <select
            name="tipo"
            value={formData.tipo}
            onChange={handleChange}
          >
            <option value="Discente">Discente</option>
            <option value="Docente">Docente</option>
            <option value="Externo">Externo</option>
          </select>
        </label>

        <label>
          Senha:
          <input
            type="password"
            name="senha"
            value={formData.senha}
            onChange={handleChange}
          />
        </label>

        <button 
          type="submit" 
          className="cadastro-btn" 
          disabled={loading}
        >
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>

        <button 
          type="button" 
          className="secondary-btn" 
          onClick={onBack}
        >
          Voltar
        </button>
      </form>

      {status && <p className="mensagem">{status}</p>}
    </div>
  );
}
