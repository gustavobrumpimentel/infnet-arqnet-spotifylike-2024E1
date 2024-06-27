package br.edu.infnet.spotifylike.service;

import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.infnet.spotifylike.application.SongService;
import br.edu.infnet.spotifylike.domain.Song;
import br.edu.infnet.spotifylike.repository.SongRepository;

@SpringBootTest
public class SongServiceTest {

  @MockBean
  private SongRepository repository;

  @Autowired
  private SongService service;

  @Test
  public void should_get_song_by_id_with_success() {
    UUID songId = UUID.randomUUID();

    Song song = new Song();
    song.setId(songId);
    song.setName("Dummy Song");
    song.setDuration(180);

    Optional<Song> optionalSong = Optional.of(song);
    given(repository.findById(songId)).willReturn(optionalSong);

    Optional<Song> expected = service.getSong(songId);
    Assertions.assertTrue(expected.isPresent());
    Assertions.assertSame(expected, optionalSong);
  }
}
