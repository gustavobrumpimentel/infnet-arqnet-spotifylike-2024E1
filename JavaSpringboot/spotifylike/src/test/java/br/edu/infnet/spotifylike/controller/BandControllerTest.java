package br.edu.infnet.spotifylike.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import br.edu.infnet.spotifylike.application.BandService;
import br.edu.infnet.spotifylike.domain.Band;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.infnet.spotifylike.SpotifylikeApplication;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpotifylikeApplication.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = BandController.class)
public class BandControllerTest {

    @MockBean
    private BandService bandService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_get_band_with_sucess() throws Exception {
        // Arrange
        Band band = new Band();

        band.setId(UUID.randomUUID());
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        ArrayList<Band> bands = new ArrayList<>();
        bands.add(band);

        given(bandService.getAll()).willReturn(bands);

        mvc.perform(get("/band").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(band.getName())))
                .andExpect(jsonPath("$[0].id", is(band.getId().toString())))
                .andExpect(jsonPath("$[0].description", is(band.getDescription())));
    }

    @Test
    public void should_get_band_with_id_sucess() throws Exception {
        UUID bandId = UUID.randomUUID();

        // Arrange
        Band band = new Band();
        band.setId(bandId);
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        Optional<Band> optionalBand = Optional.of(band);
        given(this.bandService.getBand(bandId)).willReturn(optionalBand);

        mvc.perform(get("/band/" + bandId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(band.getName())))
                .andExpect(jsonPath("$[0].id", is(band.getId().toString())))
                .andExpect(jsonPath("$[0].description", is(band.getDescription())));

    }

    @Test
    public void should_get_band_with_id_not_found() throws Exception {

        // Arrange
        UUID bandId = UUID.randomUUID();
        given(this.bandService.getBand(bandId)).willReturn(Optional.empty());

        mvc.perform(get("/band/" + bandId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_post_band_with_sucess() throws Exception {
        UUID bandId = UUID.randomUUID();

        // Arrange
        Band band = new Band();
        band.setId(bandId);
        band.setName("Dummy Band");
        band.setDescription("Dummy Description");

        mvc.perform(post("/band")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(band)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].name", is(band.getName())))
                .andExpect(jsonPath("$[0].id", is(band.getId().toString())))
                .andExpect(jsonPath("$[0].description", is(band.getDescription())));

    }

}