import React, { useState } from 'react';
import './PasswordResetModal.css';

export default function PasswordResetModal({ onClose, verificarEmailExiste }) {
  const [email, setEmail] = useState('');
  const [status, setStatus] = useState(null); // null | 'success' | 'error'
  const [mensagem, setMensagem] = useState('');

  const handleEnviar = () => {
    if (verificarEmailExiste(email)) {
      setStatus('success');
      setMensagem(`Foi enviada uma nova senha para o endereço de email ${email}`);
    } else {
      setStatus('error');
      setMensagem('Endereço de email não encontrado');
    }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        {status === 'success' ? (
          <>
            <p className="success-text">{mensagem}</p>
            <button className="modal-btn" onClick={onClose}>Fechar</button>
          </>
        ) : (
          <>
            <h3>Recuperar senha</h3>
            <label htmlFor="recuperar-email">Digite seu email:</label>
            <input
              id="recuperar-email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="seu@email.com"
              required
            />
            {status === 'error' && <p className="error-text">{mensagem}</p>}
            <div className="modal-buttons">
              <button className="modal-btn secondary" onClick={onClose}>Cancelar</button>
              <button className="modal-btn" onClick={handleEnviar}>Enviar</button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
