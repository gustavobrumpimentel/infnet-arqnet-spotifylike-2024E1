package br.edu.infnet.aluno_app.repository;

import br.edu.infnet.aluno_app.domain.Turma;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TurmaRepository {

    private final HttpClient httpClient;

    public TurmaRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Turma getTurma(UUID id) throws Exception {
        String url = String.format("http://localhost:8080/turma/%s", id.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Turma nao encontrada.");
        }

        return deserializeTurma(response.body());
    }

    private Turma deserializeTurma(String jsonContent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonContent, Turma.class);
        } catch (JsonProcessingException e) {
            throw new Exception("Failed to deserialize Turma object: " + e.getMessage());
        }
    }
}
