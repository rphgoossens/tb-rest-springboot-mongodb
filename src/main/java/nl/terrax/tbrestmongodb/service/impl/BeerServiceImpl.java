package nl.terrax.tbrestmongodb.service.impl;

import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.repository.BeerRepository;
import nl.terrax.tbrestmongodb.service.BeerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    public BeerServiceImpl(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Beer findByName(String name) {
        return beerRepository.findBeerByName(name);
    }

    @Override
    public void saveOrUpdateBeer(Beer beer) {
        beerRepository.save(beer);
    }

    @Override
    public void deleteBeer(String id) {
        beerRepository.deleteById(id);
    }

}
