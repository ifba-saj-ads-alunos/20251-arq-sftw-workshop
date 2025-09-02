import httpFactory from './httpFactory';

export async function createEvent(payload) {
  const body = {
    titulo: payload.titulo,
    descricao: payload.descricao,
    categoria: payload.categoria,
    dataInicio: payload.dataInicio,
    dataFim: payload.dataFim,
    vagas: String(payload.vagas ?? ''),
    palestrante: payload.palestrante,
    curriculo: payload.curriculo,
    localidade: payload.localidade,
    link: payload.link,
    sala: payload.sala,
  };
  const res = await httpFactory.post('/api/v1/events', body);
  return res.data;
}

