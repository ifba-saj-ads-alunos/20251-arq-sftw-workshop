import React, { useState } from 'react';
import './CadastroEvento.css';
import logo from '../../assets/ifba_logo.png';
import { createEvent } from '../../services/eventService';

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

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus('');
    try {
      if (!formData.titulo || !formData.descricao || !formData.dataInicio || !formData.dataFim) {
        setStatus('Erro: preencha os campos obrigatórios.');
        setLoading(false);
        return;
      }
      if (formData.localidade === 'Remota' && !formData.link) {
        setStatus('Erro: informe o link do evento remoto.');
        setLoading(false);
        return;
      }
      if (formData.localidade === 'Presencial' && !formData.sala) {
        setStatus('Erro: selecione a sala para o evento presencial.');
        setLoading(false);
        return;
      }

      await createEvent(formData);
      setStatus('Evento cadastrado e aguardando aprovação.');
      setFormData({
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
    } catch (err) {
      if (err.response && err.response.data && err.response.data.message) {
        setStatus(`Erro: ${err.response.data.message}`);
      } else {
        setStatus('Erro ao cadastrar evento.');
      }
    } finally {
      setLoading(false);
    }
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
            className='cadastro-evento'
          />
        </label>

        <label>
          Descrição:
          <textarea
            name="descricao"
            value={formData.descricao}
            onChange={handleChange}
            className='cadastro-evento'
          />
        </label>

        <label>
          Categoria:
          <select
            name="categoria"
            value={formData.categoria}
            onChange={handleChange}
            className='cadastro-evento'
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
            className='cadastro-evento'
          />
        </label>

        <label>
          Data de Fim:
          <input
            type="date"
            name="dataFim"
            value={formData.dataFim}
            onChange={handleChange}
            className='cadastro-evento'
          />
        </label>

        <label>
          Vagas:
          <input
            type="number"
            name="vagas"
            value={formData.vagas}
            onChange={handleChange}
            className='cadastro-evento'
          />
        </label>

        <label>
          Nome do Palestrante:
          <input
            type="text"
            name="palestrante"
            value={formData.palestrante}
            onChange={handleChange}
            className='cadastro-evento'
          />
        </label>

        <label>
          Currículo do Palestrante:
          <textarea
            name="curriculo"
            value={formData.curriculo}
            onChange={handleChange}
            className='cadastro-evento'
          />
        </label>

        <label>
          Localidade:
          <select
            name="localidade"
            value={formData.localidade}
            onChange={handleChange}
            className='cadastro-evento'
          >
            <option value="Remota">Remota</option>
            <option value="Presencial">Presencial</option>
          </select>
        </label>

        {formData.localidade === 'Remota' && (
          <label>
            Link do Evento:
            <input
              type="url"
              name="link"
              value={formData.link}
              onChange={handleChange}
              placeholder="https://exemplo.com"
              className='cadastro-evento'
            />
          </label>
        )}

        {formData.localidade === 'Presencial' && (
          <label>
            Sala:
            <select
              name="sala"
              value={formData.sala}
              onChange={handleChange}
              className='cadastro-evento'
            >
              <option value="">Selecione a Sala</option>
              <option value="Sala 101">Sala 101</option>
              <option value="Sala 102">Sala 102</option>
              <option value="Auditório">Auditório</option>
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

