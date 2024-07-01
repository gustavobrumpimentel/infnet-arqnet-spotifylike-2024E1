package br.edu.infnet.spotifylike.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.infnet.spotifylike.application.BandService;
import br.edu.infnet.spotifylike.domain.Band;
import br.edu.infnet.spotifylike.repository.BandRepository;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class BandServiceTest {

    @MockBean
    private BandRepository repository;

    @Autowired
    private BandService service;

    @Test
    public void should_get_all_band_with_sucess() {
        Band band = new Band();

        band.setId(UUID.randomUUID());
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        ArrayList<Band> bands = new ArrayList<>();
        bands.add(band);

        given(this.repository.findAll()).willReturn(bands);

        List<Band> expected = this.service.getAll();
        Assertions.assertArrayEquals(bands.toArray(), expected.toArray());
    }

    @Test
    public void should_get_by_id_band_with_sucess() {
        UUID bandId = UUID.randomUUID();

        Band band = new Band();
        band.setId(bandId);
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        Optional<Band> optionalBand = Optional.of(band);
        given(this.repository.findById(bandId)).willReturn(optionalBand);

        Optional<Band> expected = this.service.getBand(bandId);
        Assertions.assertTrue(expected.isPresent());
        Assertions.assertSame(expected, optionalBand);
    }

    @Test
    public void should_create_band_with_success() {
        UUID bandId = UUID.randomUUID();

        Band band = new Band();
        band.setId(bandId);
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        when(repository.save(band)).thenReturn(band); 

        this.service.create(band);

        verify(this.repository, times(1)).save(band);
    }
}