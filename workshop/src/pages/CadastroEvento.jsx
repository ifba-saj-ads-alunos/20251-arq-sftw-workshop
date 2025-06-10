import React, { useState } from 'react';
import '../styles/CadastroEvento.css';
import logo from '../assets/ifba_logo.png';

export default function CadastroEvento({ onBack }) {
  const [formData, setFormData] = useState({
    titulo: '',
    descricao: '',
    categoria: 'Palestra',
    dataInicio: '',
    dataFim: '',
    vagas: '',
    palestrante: '',
    curriculo: '',
    localidade: 'Remota',
    link: '',
    sala: '',
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

    setTimeout(() => {
      if (formData.titulo && formData.descricao && formData.dataInicio && formData.dataFim && formData.vagas && formData.palestrante && formData.curriculo) {
        // Verificação adicional dos campos condicionais
        if (formData.localidade === 'Remota' && !formData.link) {
          setStatus('Erro: informe o link do evento remoto.');
        } else if (formData.localidade === 'Presencial' && !formData.sala) {
          setStatus('Erro: selecione a sala para o evento presencial.');
        } else {
          setStatus('Evento cadastrado e aguardando aprovação.');
        }
      } else {
        setStatus('Erro: preencha todos os campos corretamente.');
      }
      setLoading(false);
    }, 1000);
  };

  return (
    <div className="cadastro-container">
      <img 
        src={logo}
        alt="Logo" 
        className="logo"
      />

      <h2>Cadastro de Evento</h2>

      <form onSubmit={handleSubmit} className="cadastro-form">
        <label>
          Título:
          <input
            type="text"
            name="titulo"
            value={formData.titulo}
            onChange={handleChange}
          />
        </label>

        <label>
          Descrição:
          <textarea
            name="descricao"
            value={formData.descricao}
            onChange={handleChange}
          />
        </label>

        <label>
          Categoria:
          <select
            name="categoria"
            value={formData.categoria}
            onChange={handleChange}
          >
            <option value="Palestra">Palestra</option>
            <option value="Minicurso">Minicurso</option>
            <option value="Treinamento">Treinamento</option>
            <option value="Apresentação">Apresentação</option>
          </select>
        </label>

        <label>
          Data de Início:
          <input
            type="date"
            name="dataInicio"
            value={formData.dataInicio}
            onChange={handleChange}
          />
        </label>

        <label>
          Data de Fim:
          <input
            type="date"
            name="dataFim"
            value={formData.dataFim}
            onChange={handleChange}
          />
        </label>

        <label>
          Vagas:
          <input
            type="number"
            name="vagas"
            value={formData.vagas}
            onChange={handleChange}
          />
        </label>

        <label>
          Nome do Palestrante:
          <input
            type="text"
            name="palestrante"
            value={formData.palestrante}
            onChange={handleChange}
          />
        </label>

        <label>
          Currículo do Palestrante:
          <textarea
            name="curriculo"
            value={formData.curriculo}
            onChange={handleChange}
          />
        </label>

        <label>
          Localidade:
          <select
            name="localidade"
            value={formData.localidade}
            onChange={handleChange}
          >
            <option value="Remota">Remota</option>
            <option value="Presencial">Presencial</option>
          </select>
        </label>

        {/* Campo Condicional para Link */}
        {formData.localidade === 'Remota' && (
          <label>
            Link do Evento:
            <input
              type="url"
              name="link"
              value={formData.link}
              onChange={handleChange}
              placeholder="https://exemplo.com"
            />
          </label>
        )}

        {/* Campo Condicional para Sala */}
        {formData.localidade === 'Presencial' && (
          <label>
            Sala:
            <select
              name="sala"
              value={formData.sala}
              onChange={handleChange}
            >
              <option value="">Selecione a Sala</option>
              <option value="Sala 101">Sala 101</option>
              <option value="Sala 102">Sala 102</option>
              <option value="Auditório">Auditório</option>
              {/* Futuramente populado dinamicamente */}
            </select>
          </label>
        )}

        <button type="submit" className="cadastro-btn" disabled={loading}>
          {loading ? 'Cadastrando...' : 'Cadastrar Evento'}
        </button>

        <button type="button" className="secondary-btn" onClick={onBack}>
          Voltar
        </button>
      </form>

      {status && <p className="mensagem">{status}</p>}
    </div>
  );
}
