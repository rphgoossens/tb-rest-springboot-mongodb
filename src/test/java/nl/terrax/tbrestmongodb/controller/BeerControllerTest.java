package nl.terrax.tbrestmongodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.model.Brewery;
import nl.terrax.tbrestmongodb.model.builder.BeerBuilder;
import nl.terrax.tbrestmongodb.model.builder.BreweryBuilder;
import nl.terrax.tbrestmongodb.service.BeerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerService beerServiceMock;

    @Test
    void getAllBeers() throws Exception {
        final Beer beerA = newTestBeerWithId("42", "Terra10 Gold", "Terrax Micro-Brewery Inc.", "The Netherlands");
        final Beer beerB = newTestBeerWithId("66", "Terra10 Blond", "Terrax Micro-Brewery Inc.", "The Netherlands");
        final List<Beer> allBeers = Arrays.asList(beerA, beerB);

        when(beerServiceMock.findAll()).thenReturn(allBeers);

        mockMvc.perform(get("/beers/1.0/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("42")))
                .andExpect(jsonPath("$[0].name", is("Terra10 Gold")))
                .andExpect(jsonPath("$[0].brewery.name", is("Terrax Micro-Brewery Inc.")))
                .andExpect(jsonPath("$[0].brewery.country", is("The Netherlands")))
                .andExpect(jsonPath("$[1].id", is("66")))
                .andExpect(jsonPath("$[1].name", is("Terra10 Blond")))
                .andExpect(jsonPath("$[1].brewery.name", is("Terrax Micro-Brewery Inc.")))
                .andExpect(jsonPath("$[1].brewery.country", is("The Netherlands")));

        verify(beerServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(beerServiceMock);
    }

    @Test
    void getBeerByName() throws Exception {
        final String beerId = "42";
        final String beerName = "Terra10 Gold";
        final String breweryName = "Terrax Micro-Brewery Inc.";
        final String breweryCountry = "The Netherlands";
        final Beer oneBeer = newTestBeerWithId(beerId, beerName, breweryName, breweryCountry);

        when(beerServiceMock.findByName(eq(beerName))).thenReturn(oneBeer);

        mockMvc.perform(get("/beers/1.0/{beerName}", beerName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerId)))
                .andExpect(jsonPath("$.name", is(beerName)))
                .andExpect(jsonPath("$.brewery.name", is(breweryName)))
                .andExpect(jsonPath("$.brewery.country", is(breweryCountry)));

        verify(beerServiceMock, times(1)).findByName(eq(beerName));
        verifyNoMoreInteractions(beerServiceMock);
    }

    @Test
    void saveOrUpdateBeer() throws Exception {
        final String beerName = "Terra10 Gold";
        final String breweryName = "Terrax Micro-Brewery Inc.";
        final String breweryCountry = "The Netherlands";
        final Beer beer = newTestBeer(beerName, breweryName, breweryCountry);

        mockMvc.perform(post("/beers/1.0/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().string("Beer added successfully"));

        final ArgumentCaptor<Beer> beerArgumentCaptor = ArgumentCaptor.forClass(Beer.class);
        verify(beerServiceMock, times(1)).saveOrUpdateBeer(beerArgumentCaptor.capture());
        verifyNoMoreInteractions(beerServiceMock);

        final Beer beerArgument = beerArgumentCaptor.getValue();
        assertNull(beerArgument.getId());
        assertEquals(beerName, beerArgument.getName());
        assertEquals(breweryName, beerArgument.getBrewery().getName());
        assertEquals(breweryCountry, beerArgument.getBrewery().getCountry());
    }

    @Test
    void deleteBeer() throws Exception {
        final Beer beerMock = mock(Beer.class);
        final String beerName = "Terra10 Lager";

        when(beerMock.getId()).thenReturn("42");
        when(beerServiceMock.findByName(eq(beerName))).thenReturn(beerMock);

        mockMvc.perform(delete("/beers/1.0/{beerName}", beerName))
                .andExpect(status().isOk());

        verify(beerServiceMock, times(1)).findByName(eq(beerName));
        verify(beerServiceMock, times(1)).deleteBeer(eq("42"));
        verifyNoMoreInteractions(beerServiceMock);
    }

    @SuppressWarnings("SameParameterValue")
    private Beer newTestBeer(String name, String breweryName, String breweryCountry) {
        Brewery brewery = new BreweryBuilder()
                .setName(breweryName)
                .setCountry(breweryCountry)
                .createBrewery();
        return new BeerBuilder()
                .setName(name)
                .setBrewery(brewery)
                .createBeer();
    }

    @SuppressWarnings("SameParameterValue")
    private Beer newTestBeerWithId(String id, String name, String breweryName, String breweryCountry) {
        Brewery brewery = new BreweryBuilder()
                .setName(breweryName)
                .setCountry(breweryCountry)
                .createBrewery();
        return new BeerBuilder()
                .setId(id)
                .setName(name)
                .setBrewery(brewery)
                .createBeer();
    }

}