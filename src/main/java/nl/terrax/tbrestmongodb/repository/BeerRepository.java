package nl.terrax.tbrestmongodb.repository;

import nl.terrax.tbrestmongodb.model.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BeerRepository extends MongoRepository<Beer, String> {

    Beer findBeerByName(String name);

}
