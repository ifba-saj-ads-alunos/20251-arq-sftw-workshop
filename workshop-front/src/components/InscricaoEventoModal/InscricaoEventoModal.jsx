import React from 'react';
import './InscricaoEventoModal.css';

export default function InscricaoEventoModal({ evento, onConfirmar, onCancelar }) {
  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        <h3>Confirmação de Inscrição</h3>
        <p>Deseja inscrever-se no evento <strong>{evento.titulo}</strong>?</p>
        <div className="modal-buttons">
          <button className="modal-btn confirmar" onClick={() => onConfirmar(evento)}>Confirmar</button>          
          <button className="modal-btn cancelar" onClick={onCancelar}>Cancelar</button>
        </div>
      </div>
    </div>
  );
}
