package br.ifba.ads.workshop.web.dtos;

public record CreateEventRequestDto(
        String titulo,
        String descricao,
        String categoria,
        String dataInicio, // yyyy-MM-dd
        String dataFim,    // yyyy-MM-dd
        String vagas,
        String palestrante, // not persisted yet
        String curriculo,   // not persisted yet
        String localidade,  // Remota | Presencial
        String link,        // for Remota
        String sala         // for Presencial
,
        java.util.List<SessionInput> sessions
) {

    public record SessionInput(String startsAt, String endsAt, Integer capacity, String room) {}

}

