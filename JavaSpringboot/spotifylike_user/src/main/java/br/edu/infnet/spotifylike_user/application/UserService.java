package br.edu.infnet.spotifylike_user.application;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.spotifylike_user.domain.Card;
import br.edu.infnet.spotifylike_user.domain.Plan;
import br.edu.infnet.spotifylike_user.domain.Playlist;
import br.edu.infnet.spotifylike_user.domain.Song;
import br.edu.infnet.spotifylike_user.domain.User;
import br.edu.infnet.spotifylike_user.repository.UserRepository;
import br.edu.infnet.spotifylike_user.repository.PlanRepository;
import br.edu.infnet.spotifylike_user.repository.PlaylistRepository;
import br.edu.infnet.spotifylike_user.repository.SongRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public Optional<User> getUser(UUID id) {
        return this.userRepository.findById(id);
    }

    public User createAccount(String name, UUID planId, Card card) {
        Plan plan = this.planRepository.findById(planId).get();

        User user = new User(name, plan, card);

        this.userRepository.save(user);

        return user;
    }

    public void favoriteSong(UUID id, UUID songId) {
        User user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        user.favoriteSong(song);

        this.userRepository.save(user);
    }

    public void unfavoriteSong(UUID id, UUID songId) {
        User user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        user.unfavoriteSong(song);

        this.userRepository.save(user);
    }

    private Song verifySong(UUID songId) {

        Song song = this.songRepository.findById(songId).get();

        return song;
    }

    private Playlist verifyPlaylist(UUID playlistId) {

        Playlist playlist = this.playlistRepository.findById(playlistId).get();

        return playlist;
    }

    public void createPlaylist(UUID id, String name, boolean isPublic) {
        User user = this.userRepository.findById(id).get();

        user.createPlaylist(name, isPublic);
    }

    public void addSongToPlaylist(UUID id, UUID songId, UUID playlistId) {
        User user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        user.addSongToPlaylist(song, playlist);

        this.userRepository.save(user);
    }

    public void removeSongFromPlaylist(UUID id, UUID songId, UUID playlistId) {
        User user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        user.removeSongFromPlaylist(song, playlist);

        this.userRepository.save(user);
    }

    public void subscribe(UUID id, UUID planId, Card card) {
        User user = this.userRepository.findById(id).get();

        Plan plan = this.planRepository.findById(id).get();

        user.subscribe(plan, card);

        this.userRepository.save(user);
    }
}
