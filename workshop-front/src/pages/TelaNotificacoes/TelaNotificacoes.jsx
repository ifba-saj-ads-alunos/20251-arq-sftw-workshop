import React, { useEffect, useState } from 'react';
import '../TelaPrincipal/TelaPrincipal.css';
import './TelaNotificacoes.css';
import { fetchNotifications, markAsRead, markAllRead } from '../../services/notificationService';

export default function TelaNotificacoes({ onVoltar }) {
  const [notificacoes, setNotificacoes] = useState([]);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchNotifications();
        const list = data || [];
        setNotificacoes(list);

        // do not auto-mark here anymore; we'll provide per-item click and a button
      } catch (err) {
        console.error(err);
      }
    })();
  }, []);

  const handleClickNotification = async (n) => {
    try {
      if (!n.read) {
        await markAsRead(n.id);
        setNotificacoes(prev => prev.map(item => item.id === n.id ? { ...item, read: true } : item));
        // notify other components (e.g., header) to refresh unread count
        window.dispatchEvent(new CustomEvent('notificationsUpdated'));
      }
    } catch (err) {
      console.error('Erro ao marcar notificação como lida', err);
    }
  };

  const handleMarkAll = async () => {
    try {
      await markAllRead();
      setNotificacoes(prev => prev.map(n => ({ ...n, read: true })));
      window.dispatchEvent(new CustomEvent('notificationsUpdated'));
    } catch (err) {
      console.error('Erro ao marcar todas como lidas', err);
    }
  };

  return (
    <div className="principal-container">
      <h2>Notificações</h2>
      <div className="notificacoes-list">
        {notificacoes.length === 0 ? (
          <p>Nenhuma notificação.</p>
        ) : (
          <>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 8 }}>
              <button className="secondary-btn" onClick={handleMarkAll}>Marcar todas como lidas</button>
            </div>
            {notificacoes.map(n => (
              <div key={n.id} className={`notificacao-item ${n.read ? 'read' : 'unread'}`} onClick={() => handleClickNotification(n)} style={{ cursor: 'pointer' }}>
                <h4>{n.title} {n.read ? null : <span className="dot-unread" />}</h4>
                <p>{n.message}</p>
                <small>{n.sentAt}</small>
              </div>
            ))}
          </>
        )}
      </div>
      <button className="secondary-btn" onClick={onVoltar}>Voltar</button>
    </div>
  );
}
