import React, { useState, useCallback } from 'react';
import { SnackbarContext } from './SnackbarContext';
import './Snackbar.css';

export const SnackbarProvider = ({ children }) => {
  const [snackbars, setSnackbars] = useState([]);

  const showSnackbar = useCallback((message, type = 'info', duration = 5000) => {
    const id = Date.now() + Math.random();
    const newSnackbar = { id, message, type, duration };
    
    setSnackbars(prev => [...prev, newSnackbar]);
    
    // Auto-remove snackbar após o tempo especificado
    setTimeout(() => {
      setSnackbars(prev => prev.filter(snackbar => snackbar.id !== id));
    }, duration);
  }, []);

  const removeSnackbar = useCallback((id) => {
    setSnackbars(prev => prev.filter(snackbar => snackbar.id !== id));
  }, []);

  const showSuccess = useCallback((message, duration) => {
    showSnackbar(message, 'success', duration);
  }, [showSnackbar]);

  const showError = useCallback((message, duration) => {
    showSnackbar(message, 'error', duration);
  }, [showSnackbar]);

  const showWarning = useCallback((message, duration) => {
    showSnackbar(message, 'warning', duration);
  }, [showSnackbar]);

  const showInfo = useCallback((message, duration) => {
    showSnackbar(message, 'info', duration);
  }, [showSnackbar]);

  return (
    <SnackbarContext.Provider value={{ 
      showSnackbar, 
      showSuccess, 
      showError, 
      showWarning, 
      showInfo,
      removeSnackbar 
    }}>
      {children}
      <SnackbarContainer snackbars={snackbars} onRemove={removeSnackbar} />
    </SnackbarContext.Provider>
  );
};

const SnackbarContainer = ({ snackbars, onRemove }) => {
  if (snackbars.length === 0) return null;

  return (
    <div className="snackbar-container">
      {snackbars.map(snackbar => (
        <Snackbar 
          key={snackbar.id} 
          snackbar={snackbar} 
          onRemove={onRemove}
        />
      ))}
    </div>
  );
};

const Snackbar = ({ snackbar, onRemove }) => {
  const getSnackbarClass = (type) => {
    const baseClass = 'snackbar';
    switch (type) {
      case 'success':
        return `${baseClass} snackbar-success`;
      case 'error':
        return `${baseClass} snackbar-error`;
      case 'warning':
        return `${baseClass} snackbar-warning`;
      case 'info':
      default:
        return `${baseClass} snackbar-info`;
    }
  };

  return (
    <div className={getSnackbarClass(snackbar.type)}>
      <span className="snackbar-message">{snackbar.message}</span>
      <button 
        className="snackbar-close"
        onClick={() => onRemove(snackbar.id)}
        aria-label="Fechar notificação"
      >
        ×
      </button>
    </div>
  );
};
