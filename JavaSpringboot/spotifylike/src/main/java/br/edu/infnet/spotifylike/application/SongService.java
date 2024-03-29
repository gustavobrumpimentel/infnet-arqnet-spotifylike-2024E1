package br.edu.infnet.spotifylike.application;

import br.edu.infnet.spotifylike.domain.Song;
import br.edu.infnet.spotifylike.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SongService {

    @Autowired
    private SongRepository repository;

    public Optional<Song> getSong(UUID id) {
        return this.repository.findById(id);
    }

}
