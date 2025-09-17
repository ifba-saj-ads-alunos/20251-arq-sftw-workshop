import React, { useState } from 'react';
import './TelaSugestoes.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaSugestoes({ onVoltar }) {
  const [sugestao, setSugestao] = useState('');
  const [categoria, setCategoria] = useState('');
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!sugestao.trim() || !categoria.trim()) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }
    
    // Here would be the API call to submit suggestion
    alert('Sugestão enviada com sucesso! Obrigado pelo seu feedback.');
    
    // Reset form
    setSugestao('');
    setCategoria('');
    setNome('');
    setEmail('');
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
          <h1>Enviar Sugestão</h1>
        </div>

        <div className="sugestoes-container">
          <form onSubmit={handleSubmit} className="sugestoes-form">
            <div className="form-group">
              <label htmlFor="categoria">Categoria*</label>
              <select
                id="categoria"
                value={categoria}
                onChange={(e) => setCategoria(e.target.value)}
                required
              >
                <option value="">Selecione uma categoria</option>
                <option value="evento">Sugestão de Evento</option>
                <option value="melhoria">Melhoria do Sistema</option>
                <option value="problema">Relatar Problema</option>
                <option value="outro">Outro</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="nome">Seu Nome (opcional)</label>
              <input
                type="text"
                id="nome"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                placeholder="Digite seu nome"
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Seu E-mail (opcional)</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Digite seu e-mail para retorno"
              />
            </div>

            <div className="form-group">
              <label htmlFor="sugestao">Sua Sugestão*</label>
              <textarea
                id="sugestao"
                value={sugestao}
                onChange={(e) => setSugestao(e.target.value)}
                placeholder="Descreva sua sugestão detalhadamente..."
                rows="6"
                required
              />
            </div>

            <div className="form-buttons">
              <button type="submit" className="primary-btn">Enviar Sugestão</button>
              <button type="button" className="secondary-btn" onClick={onVoltar}>Voltar</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}