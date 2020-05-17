package nl.terrax.tbrestmongodb.controller;

import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beers")
public class BeerController {
    @Autowired
    private BeerService beerService;

    @GetMapping(value = "/")
    public List<Beer> getAllBeers() {
        return beerService.findAll();
    }

    @GetMapping(value = "/{beerName}")
    public Beer getBeerByName(@PathVariable("beerName") String name) {
        return beerService.findByName(name);
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> saveOrUpdateBeer(@RequestBody Beer beer) {
        beerService.saveOrUpdateBeer(beer);
        return new ResponseEntity<>("Beer added successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{beerName}")
    public void deleteBeer(@PathVariable("beerName") String name) {
        beerService.deleteBeer(beerService.findByName(name).getId());
    }
}
