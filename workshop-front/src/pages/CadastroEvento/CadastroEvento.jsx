import React, { useState, useEffect } from 'react';
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
    sessions: []
  });

  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const [rooms, setRooms] = useState([]);

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

      // validate sessions
      const sessions = formData.sessions || [];
      // Convert and validate datetimes
      const parsed = [];
      for (let i = 0; i < sessions.length; i++) {
        const s = sessions[i];
        if (!s.startsAt || !s.endsAt) {
          setStatus(`Erro: informe início e fim para a sessão ${i + 1}.`);
          setLoading(false);
          return;
        }
        const start = new Date(s.startsAt);
        const end = new Date(s.endsAt);
        if (isNaN(start.getTime()) || isNaN(end.getTime())) {
          setStatus(`Erro: formato de data/hora inválido na sessão ${i + 1}.`);
          setLoading(false);
          return;
        }
        if (start >= end) {
          setStatus(`Erro: início deve ser anterior ao fim na sessão ${i + 1}.`);
          setLoading(false);
          return;
        }
        const capacity = s.capacity != null && s.capacity !== '' ? Number(s.capacity) : null;
        if (capacity != null && (Number.isNaN(capacity) || capacity < 0)) {
          setStatus(`Erro: capacidade inválida na sessão ${i + 1}.`);
          setLoading(false);
          return;
        }
        parsed.push({ startsAt: start.toISOString(), endsAt: end.toISOString(), capacity, room: s.room || null });
      }

      // check overlaps: sort by startsAt
      parsed.sort((a, b) => new Date(a.startsAt) - new Date(b.startsAt));
      for (let i = 1; i < parsed.length; i++) {
        const prevEnd = new Date(parsed[i - 1].endsAt);
        const curStart = new Date(parsed[i].startsAt);
        if (curStart < prevEnd) {
          setStatus(`Erro: sessões ${i} e ${i + 1} estão sobrepostas.`);
          setLoading(false);
          return;
        }
      }

      // prepare payload, ensure sessions are in ISO format
      const payload = { ...formData };
      payload.sessions = parsed;

      await createEvent(payload);
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
        sessions: []
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

  useEffect(() => {
    let mounted = true;
    (async () => {
      try {
        const res = await fetch('/api/v1/rooms');
        if (!res.ok) return;
        const data = await res.json();
        if (mounted) setRooms(data);
      } catch {
        // ignore network errors
      }
    })();
    return () => { mounted = false; };
  }, []);

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
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <select
                name="sala"
                value={formData.sala}
                onChange={handleChange}
                className='cadastro-evento'
              >
                <option value="">Selecione a Sala</option>
                {rooms.map(r => (
                  <option key={r.id} value={r.name}>{r.name} {r.capacity ? `(${r.capacity})` : ''}</option>
                ))}
              </select>
              <button type="button" className="secondary-btn" onClick={async () => {
                const name = prompt('Nome da nova sala:');
                if (!name) return;
                const capacityStr = prompt('Capacidade (número, opcional):');
                const capacity = capacityStr ? Number(capacityStr) : null;
                try {
                  const res = await fetch('/api/v1/rooms', {
                    method: 'POST', headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ name, capacity, location: '' })
                  });
                  if (!res.ok) {
                    alert('Falha ao criar sala');
                    return;
                  }
                  // reload rooms
                  const data = await res.json();
                  setRooms(prev => [...prev, data]);
                  setFormData(prev => ({ ...prev, sala: data.name }));
                } catch {
                  alert('Erro ao criar sala');
                }
              }}>Adicionar Sala</button>
            </div>
          </label>
        )}

        <div className="sessions-block">
          <h3>Sessões</h3>
          {formData.sessions.length === 0 && <p>Nenhuma sessão adicionada.</p>}
          {formData.sessions.map((s, idx) => (
            <div key={idx} className="session-item">
              <label>
                Início:
                <input type="datetime-local" name={`session_starts_${idx}`} value={s.startsAt} onChange={(e) => {
                  const val = e.target.value;
                  setFormData(prev => {
                    const copy = { ...prev };
                    copy.sessions = copy.sessions.map((ss, i) => i === idx ? { ...ss, startsAt: val } : ss);
                    return copy;
                  });
                }} />
              </label>
              <label>
                Fim:
                <input type="datetime-local" name={`session_ends_${idx}`} value={s.endsAt} onChange={(e) => {
                  const val = e.target.value;
                  setFormData(prev => {
                    const copy = { ...prev };
                    copy.sessions = copy.sessions.map((ss, i) => i === idx ? { ...ss, endsAt: val } : ss);
                    return copy;
                  });
                }} />
              </label>
              <label>
                Capacidade:
                <input type="number" value={s.capacity || ''} onChange={(e) => {
                  const val = e.target.value;
                  setFormData(prev => {
                    const copy = { ...prev };
                    copy.sessions = copy.sessions.map((ss, i) => i === idx ? { ...ss, capacity: val } : ss);
                    return copy;
                  });
                }} />
              </label>
              <label>
                Sala:
                <select value={s.room || ''} onChange={(e) => {
                  const val = e.target.value;
                  setFormData(prev => {
                    const copy = { ...prev };
                    copy.sessions = copy.sessions.map((ss, i) => i === idx ? { ...ss, room: val } : ss);
                    return copy;
                  });
                }}>
                  <option value="">Nenhuma</option>
                  {rooms.map(r => <option key={r.id} value={r.name}>{r.name}</option>)}
                </select>
              </label>
              <button type="button" className="secondary-btn" onClick={() => {
                setFormData(prev => ({ ...prev, sessions: prev.sessions.filter((_, i) => i !== idx) }));
              }}>Remover Sessão</button>
            </div>
          ))}

          <button type="button" className="secondary-btn" onClick={() => {
            setFormData(prev => ({ ...prev, sessions: [...(prev.sessions || []), { startsAt: '', endsAt: '', capacity: null, room: '' }] }));
          }}>Adicionar Sessão</button>
        </div>

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

