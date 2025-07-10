import React, { useState } from 'react';
import './CadastroUsuario.css';
import logo from '../../assets/ifba_logo.png';

export default function CadastroUsuario({ onBack }) {
  const [formData, setFormData] = useState({
    nome: '',
    cpf: '',
    email: '',
    tipo: 'Discente',
    senha: '',
    pcd: 'nao',
    tipoDeficiencia: '',
    acessibilidade: ''
  });

  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus('');

    try {
      const response = await fetch('/api/v1/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        setStatus('Usuário cadastrado com sucesso!');
      } else {
        setStatus('Erro ao cadastrar usuário.');
      }
    } catch (err) {
      setStatus('Erro ao conectar com o servidor.');
    }

    setLoading(false);
  };

  return (
    <div className="cadastro-container">
      <img src={logo} alt="Logo" className="logo cadastro-usuario" />
      <form onSubmit={handleSubmit} className="cadastro-form">
        <label className='cadastro-usuario'>
          Nome:
          <input type="text" name="nome" value={formData.nome} onChange={handleChange} className='cadastro-usuario'/>
        </label>

        <label className='cadastro-usuario'>
          CPF:
          <input type="text" name="cpf" value={formData.cpf} onChange={handleChange} className='cadastro-usuario' />
        </label>

        <label className='cadastro-usuario'>
          Email:
          <input type="email" name="email" value={formData.email} onChange={handleChange} className='cadastro-usuario' />
        </label>

        <label className='cadastro-usuario'>
          Perfil:
          <select name="tipo" value={formData.tipo} onChange={handleChange} className='cadastro-usuario'>
            <option value="Discente">Discente</option>
            <option value="Docente">Docente</option>
            <option value="Externo">Externo</option>
          </select>
        </label>

        <label className='cadastro-usuario'>
          Senha:
          <input type="password" name="senha" value={formData.senha} onChange={handleChange} className='cadastro-usuario'/>
        </label>

        <label className='cadastro-usuario'>Pessoa com deficiência?</label>
        <div className="radio-group  cadastro-usuario">
          <label>
            <input
              type="radio"
              name="pcd"
              value="nao"
              checked={formData.pcd === 'nao'}
              onChange={handleChange}
            />
            Não
          </label>
          <label>
            <input
              type="radio"
              name="pcd"
              value="sim"
              checked={formData.pcd === 'sim'}
              onChange={handleChange}
            />
            Sim
          </label>
        </div>

        {formData.pcd === 'sim' && (
          <>
            <label className='cadastro-usuario'>
              Tipo:
              <select
                name="tipoDeficiencia"
                value={formData.tipoDeficiencia}
                onChange={handleChange} className='cadastro-usuario'
              >
                <option value="">Selecione</option>
                <option value="Deficiência Física">Deficiência Física</option>
                <option value="Deficiência Visual">Deficiência Visual</option>
                <option value="Deficiência Auditiva">Deficiência Auditiva</option>
                <option value="Deficiência Mental">Deficiência Mental</option>
                <option value="Neurodivergência">Neurodivergência</option>
              </select>
            </label>

            <label className='cadastro-usuario'>
              Solicitação para Acessibilidade:
              <textarea
                name="acessibilidade"
                value={formData.acessibilidade}
                onChange={handleChange}
                placeholder="Digite aqui como podemos garantir que sua necessidade seja atendida..."
                className='cadastro-usuario'
              ></textarea>
            </label>
          </>
        )}

        <button type="submit" className="cadastro-btn cadastro-usuario" disabled={loading}>
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>

        <button type="button" className="secondary-btn cadastro-usuario" onClick={onBack}>
          Voltar
        </button>
      </form>

      {status && <p className="mensagem">{status}</p>}
    </div>
  );
}
