package br.edu.infnet.spotifylike.application;

import br.edu.infnet.spotifylike.domain.Band;
import br.edu.infnet.spotifylike.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandService {

    @Autowired
    private BandRepository repository;

    public void create(Band band) {
        this.repository.save(band);
    }

    public Optional<Band> getBand(UUID id) {
        return this.repository.findById(id);
    }

    public List<Band> getAll() {
        return this.repository.findAll();
    }

}
