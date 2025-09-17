import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaSugestoes.css';

export default function TelaSugestoes({ onVoltar }) {
  const [sugestao, setSugestao] = useState('');
  const [categoria, setCategoria] = useState('');
  const [enviada, setEnviada] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (sugestao.trim() && categoria) {
      // TODO: Implement API call to save suggestion
      console.log('Sugestão enviada:', { categoria, sugestao });
      setEnviada(true);
      setTimeout(() => {
        setEnviada(false);
        setSugestao('');
        setCategoria('');
      }, 3000);
    }
  };

  return (
    <div className="principal-container">
      <h2>Sugestões</h2>
      
      {enviada ? (
        <div className="sucesso-container">
          <h3>✓ Sugestão enviada com sucesso!</h3>
          <p>Obrigado por sua contribuição. Sua sugestão será analisada pela equipe.</p>
        </div>
      ) : (
        <form onSubmit={handleSubmit} className="sugestoes-form">
          <div className="campo-container">
            <label htmlFor="categoria">Categoria:</label>
            <select
              id="categoria"
              value={categoria}
              onChange={(e) => setCategoria(e.target.value)}
              required
            >
              <option value="">Selecione uma categoria</option>
              <option value="eventos">Sugestão de Evento</option>
              <option value="palestrantes">Sugestão de Palestrante</option>
              <option value="melhorias">Melhoria no Sistema</option>
              <option value="outros">Outros</option>
            </select>
          </div>

          <div className="campo-container">
            <label htmlFor="sugestao">Sua sugestão:</label>
            <textarea
              id="sugestao"
              value={sugestao}
              onChange={(e) => setSugestao(e.target.value)}
              placeholder="Descreva sua sugestão aqui..."
              rows="6"
              required
            />
          </div>

          <div className="buttons-container">
            <button type="submit" className="primary-btn">Enviar Sugestão</button>
            <button type="button" className="secondary-btn" onClick={onVoltar}>Voltar</button>
          </div>
        </form>
      )}
    </div>
  );
}