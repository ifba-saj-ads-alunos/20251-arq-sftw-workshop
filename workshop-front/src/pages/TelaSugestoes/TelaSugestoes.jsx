import React, { useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaSugestoes.css';
import logo from '../../assets/ifba_logo.png';

export default function TelaSugestoes({ onVoltar }) {
  const [sugestao, setSugestao] = useState('');
  const [categoria, setCategoria] = useState('');
  const [enviada, setEnviada] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (sugestao.trim() && categoria.trim()) {
      // Here you would normally send to backend
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
    <div className="tela-container">
      <div className="principal-container sugestoes">
        <div className="topo-pagina sugestoes">
          <img 
            src={logo}
            alt="Logo" 
            className="logo tela-principal"
          />
          <h1 id="h1-sugestoes">Sugestões</h1>
          <button className="secondary-btn btn-voltar sugestoes" onClick={onVoltar}>Voltar</button>
        </div>

        <div className="sugestoes-form-container">

          <div>
            <h2>Envie sua sugestão</h2>
            <p>Sua opinião é importante para melhorarmos nossos eventos!</p>
          </div>

          {enviada ? (
            <div className="sucesso-message">
              <h3>✅ Sugestão enviada com sucesso!</h3>
              <p>Obrigado pelo seu feedback. Sua sugestão foi registrada e será analisada pela nossa equipe.</p>
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="sugestoes-form">
              <div className="form-group">
                <label htmlFor="categoria">Categoria da Sugestão:</label>
                <select 
                  id="categoria"
                  value={categoria}
                  onChange={(e) => setCategoria(e.target.value)}
                  required
                >
                  <option value="">Selecione uma categoria</option>
                  <option value="evento">Sugestão de Evento</option>
                  <option value="palestrante">Sugestão de Palestrante</option>
                  <option value="melhoria">Melhoria do Sistema</option>
                  <option value="outros">Outros</option>
                </select>
              </div>

              <div className="form-group">
                <label htmlFor="sugestao">Sua Sugestão:</label>
                <textarea
                  id="sugestao"
                  value={sugestao}
                  onChange={(e) => setSugestao(e.target.value)}
                  placeholder="Descreva sua sugestão aqui..."
                  rows="5"
                  required
                ></textarea>
              </div>

              <button type="submit" className="principal-btn">
                Enviar Sugestão
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}