package br.edu.infnet.spotifylike_user.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.infnet.spotifylike_user.domain.Song;

@Repository
public class BandRepository {

    private final HttpClient httpClient;

    public BandRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Song getSong(UUID idSong) throws Exception {
        String url = String.format("http://localhost:8081/song/%s", idSong.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Could not find song.");
        }

        return deserializeSong(response.body());
    }

    private Song deserializeSong(String jsonContent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonContent, Song.class);
        } catch (JsonProcessingException e) {
            throw new Exception("Failed to deserialize Song object: " + e.getMessage());
        }
    }
}
