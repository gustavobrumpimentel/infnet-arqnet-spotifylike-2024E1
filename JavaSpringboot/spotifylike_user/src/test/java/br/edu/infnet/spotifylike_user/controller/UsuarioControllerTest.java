package br.edu.infnet.spotifylike_user.controller;

import br.edu.infnet.spotifylike_user.application.UsuarioService;
import br.edu.infnet.spotifylike_user.domain.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;


@AutoConfigureMockMvc
@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_post_usuario_with_success() throws Exception {
        
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setName("Dummy User");

        mvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(usuario.getName())))
                .andExpect(jsonPath("$.id", is(usuario.getId().toString())));
    }

    // @Test
    // public void should_post_usuario_with_name_success() throws Exception {
        
    //     // Arrange
    //     String usuarioName = "Dummy";
    //     Usuario usuario = new Usuario();
    //     usuario.setName(usuarioName);

    //     mvc.perform(post("/api/usuario/" + usuarioName)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.name", is(usuario.getName())));
    // }

    @Test
    public void should_get_usuario_by_id_success() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        String usuarioName = "Dummy";
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setName(usuarioName);

        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        given(this.service.getUser(usuarioId)).willReturn(optionalUsuario);

        mvc.perform(get("/api/usuario/" + usuarioId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(usuarioName)))
                .andExpect(jsonPath("$.id", is(usuario.getId().toString())));
    }

    @Test
    public void should_get_user_by_id_not_found() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        given(service.getUser(userId)).willReturn(Optional.empty());

        mvc.perform(get("/api/usuario/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_favorite_song_success() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID songId = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        given(service.getUser(usuarioId)).willReturn(Optional.of(usuario));

        mvc.perform(post("/api/usuario/" + usuarioId + "/favorite/" + songId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usuarioId.toString())));
    }

    @Test
    public void should_unfavorite_song_success() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID songId = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        given(service.getUser(usuarioId)).willReturn(Optional.of(usuario));

        mvc.perform(post("/api/usuario/" + usuarioId + "/unfavorite/" + songId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usuarioId.toString())));
    }

    @Test
    public void should_create_playlist_success() throws Exception {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        String playlistName = "My Playlist";
        boolean isPublic = true;
        Usuario user = new Usuario();
        user.setId(usuarioId);
        given(service.getUser(usuarioId)).willReturn(Optional.of(user));

        // Act & Assert
        mvc.perform(post("/api/usuario/" + usuarioId + "/playlist/" + playlistName + "/" + isPublic)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usuarioId.toString())));

    }

    @Test
    public void should_add_song_to_playlist_success() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID songId = UUID.randomUUID();
        UUID playlistId = UUID.randomUUID();
        Usuario user = new Usuario();
        user.setId(userId);
        given(service.getUser(userId)).willReturn(Optional.of(user));

        // Act & Assert
        mvc.perform(post("/api/usuario/" + userId + "/add/" + songId + "/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId.toString()))); // Assert user ID after modification
    }

    @Test
    public void should_remove_song_from_playlist_success() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID songId = UUID.randomUUID();
        UUID playlistId = UUID.randomUUID();
        Usuario user = new Usuario();
        user.setId(userId);
        given(service.getUser(userId)).willReturn(Optional.of(user));

        // Act & Assert
        mvc.perform(post("/api/usuario/" + userId + "/remove/" + songId + "/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId.toString()))); // Assert user ID after modification
    }

}