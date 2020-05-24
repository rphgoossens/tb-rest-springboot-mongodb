package nl.terrax.tbrestmongodb.model.builder;

import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.model.Brewery;

public class BeerBuilder {
    private String id;
    private String name;
    private Brewery brewery;

    public BeerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public BeerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BeerBuilder setBrewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public Beer createBeer() {
        return new Beer(id, name, brewery);
    }
}