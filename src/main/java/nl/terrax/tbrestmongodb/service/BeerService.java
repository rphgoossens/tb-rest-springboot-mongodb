package nl.terrax.tbrestmongodb.service;

import nl.terrax.tbrestmongodb.model.Beer;

import java.util.List;

public interface BeerService {
    List<Beer> findAll();

    Beer findByName(String name);

    void saveOrUpdateBeer(Beer beer);

    void deleteBeer(String id);

}
