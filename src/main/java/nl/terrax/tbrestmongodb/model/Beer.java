package nl.terrax.tbrestmongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("unused")
@Document("beers")
public class Beer {
    @Id
    private String id;
    private String name;
    private Brewery brewery;

    public Beer() {
    }

    public Beer(String id, String name, Brewery brewery) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }
}
