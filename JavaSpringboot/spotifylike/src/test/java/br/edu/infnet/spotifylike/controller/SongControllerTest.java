package br.edu.infnet.spotifylike.controller;

import br.edu.infnet.spotifylike.application.SongService;
import br.edu.infnet.spotifylike.domain.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpotifylikeApplication.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = SongController.class)
public class SongControllerTest {

    @MockBean
    private SongService songService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_song_with_id_success() throws Exception {
        UUID songId = UUID.randomUUID();

        // Arrange
        Song song = new Song();
        song.setId(songId);
        song.setName("Dummy Song");
        song.setDuration(180);

        Optional<Song> optionalSong = Optional.of(song);
        given(songService.getSong(songId)).willReturn(optionalSong);

        mvc.perform(get("/song/" + songId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(song.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(songId.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", is(song.getDuration())));
    }

    @Test
    public void should_get_song_with_id_not_found() throws Exception {
        UUID songId = UUID.randomUUID();

        // Arrange
        given(songService.getSong(songId)).willReturn(Optional.empty());

        mvc.perform(get("/song/" + songId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
