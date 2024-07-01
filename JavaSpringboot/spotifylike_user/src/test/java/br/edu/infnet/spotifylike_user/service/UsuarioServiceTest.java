package br.edu.infnet.spotifylike_user.service;

import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.infnet.spotifylike_user.application.UsuarioService;
import br.edu.infnet.spotifylike_user.domain.Playlist;
import br.edu.infnet.spotifylike_user.domain.Song;
import br.edu.infnet.spotifylike_user.domain.Usuario;
import br.edu.infnet.spotifylike_user.repository.BandRepository;
import br.edu.infnet.spotifylike_user.repository.PlanRepository;
import br.edu.infnet.spotifylike_user.repository.PlaylistRepository;
import br.edu.infnet.spotifylike_user.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {

  @MockBean
  private UsuarioRepository userRepository;

  @MockBean
  private PlanRepository planRepository;

  @MockBean
  private BandRepository bandRepository;

  @MockBean
  private PlaylistRepository playlistRepository;

  @Autowired
  private UsuarioService service;

  @Test
  public void should_get_user_by_id_with_success() {
    UUID userId = UUID.randomUUID();

    Usuario user = new Usuario("John Doe");
    Optional<Usuario> optionalUser = Optional.of(user);
    given(userRepository.findById(userId)).willReturn(optionalUser);

    Optional<Usuario> expected = service.getUser(userId);
    Assertions.assertTrue(expected.isPresent());
    Assertions.assertSame(expected, optionalUser);
  }

  @Test
  public void should_create_account_with_name_only() {
    Usuario user = service.createAccount("Dummy");
    Assertions.assertNotNull(user);
    Assertions.assertEquals(user.getName(), "Dummy");
  }

  @Test
  public void should_favorite_song_with_success() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID songId = UUID.randomUUID();

    Usuario user = new Usuario("Dummy");
    Song song = new Song();
    Optional<Usuario> optionalUser = Optional.of(user);
    given(userRepository.findById(userId)).willReturn(optionalUser);

    given(bandRepository.getSong(songId)).willReturn(song);

    service.favoriteSong(userId, songId);

    Playlist playlist = user.getPlaylists().stream().filter(x -> x.getName() == "Favoritas").findFirst().orElse(null);

    Assertions.assertNotNull(playlist.getSongs().stream().filter(x -> x.getId() == song.getId()).findFirst().orElse(null));
  }

  @Test
  public void should_unfavorite_song_with_success() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID songId = UUID.randomUUID();
  
    Usuario user = new Usuario("Dummy");
    Song song = new Song(); 
    user.favoriteSong(song);
    Optional<Usuario> optionalUser = Optional.of(user);

    given(userRepository.findById(userId)).willReturn(optionalUser);
  
    given(bandRepository.getSong(songId)).willReturn(song);
  
    service.unfavoriteSong(userId, songId);  

    Optional<Usuario> updatedUser = userRepository.findById(userId);
    Usuario retrievedUser = updatedUser.get(); 
  
    Playlist playlist = retrievedUser.getPlaylists().stream().filter(x -> x.getName() == "Favoritas").findFirst().orElse(null);

    Assertions.assertNull(playlist.getSongs().stream().filter(x -> x.getId() == song.getId()).findFirst().orElse(null));
  }

  // @Test
  //   public void should_add_song_to_playlist_with_success() throws Exception {
  //   UUID userId = UUID.randomUUID();
  //   UUID songId = UUID.randomUUID();
  //   UUID playlistId = UUID.randomUUID();

  //   Usuario user = new Usuario("Dummy");
  //   user.setId(userId);

  //   Song song = new Song();
  //   song.setId(songId);

  //   Playlist playlist = new Playlist("My Playlist", true);
  //   playlist.setId(playlistId);

  //   user.getPlaylists().add(playlist);
  //   Optional<Usuario> optionalUser = Optional.of(user);
  //   Optional<Playlist> optionalPlaylist = Optional.of(playlist);

  //   given(userRepository.findById(userId)).willReturn(optionalUser);

  //   given(bandRepository.getSong(songId)).willReturn(song);

  //   given(playlistRepository.findById(userId)).willReturn(optionalPlaylist);

  //   service.addSongToPlaylist(userId, songId, playlistId);

  //   Playlist retrievedPlaylist = user.getPlaylists().stream()
  //       .filter(p -> p.getId().equals(playlistId))
  //       .findFirst().orElse(null);
  //   Assertions.assertNotNull(retrievedPlaylist);

  //   Assertions.assertTrue(retrievedPlaylist.getSongs().stream()
  //       .anyMatch(s -> s.getId().equals(songId)));
  //   }

  //   @Test
  //   public void should_remove_song_from_playlist_with_success() throws Exception {
  //   UUID userId = UUID.randomUUID();
  //   UUID songId = UUID.randomUUID();
  //   UUID playlistId = UUID.randomUUID();

  //   Usuario user = new Usuario("Dummy");
  //   user.setId(userId);

  //   Song song = new Song();
  //   song.setId(songId);

  //   Playlist playlist = new Playlist("My Playlist", true);
  //   playlist.setId(playlistId);

  //   playlist.getSongs().add(song);
  //   user.getPlaylists().add(playlist);

  //   Optional<Usuario> optionalUser = Optional.of(user);
  //   Optional<Playlist> optionalPlaylist = Optional.of(playlist);

  //   given(userRepository.findById(userId)).willReturn(optionalUser);

  //   given(bandRepository.getSong(songId)).willReturn(song);

  //   given(playlistRepository.findById(userId)).willReturn(optionalPlaylist);

  //   service.removeSongFromPlaylist(userId, songId, playlistId);

  //   Playlist retrievedPlaylist = user.getPlaylists().stream()
  //       .filter(p -> p.getId().equals(playlistId))
  //       .findFirst().orElse(null);
  //   Assertions.assertNotNull(retrievedPlaylist);

  //   Assertions.assertFalse(retrievedPlaylist.getSongs().stream()
  //       .anyMatch(s -> s.getId().equals(songId)));
  //   }
}
