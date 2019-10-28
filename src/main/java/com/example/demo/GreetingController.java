package com.example.demo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
class GreetingController {

    private final GreetingRepository repository;

    private final GreetingResourceAssembler assembler;

    GreetingController(GreetingRepository repository, GreetingResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Single item
/* moving to REST for get single ID mapping
    @GetMapping("/greetings/{id}")
    Greeting one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException(id));
    }
*/
    @GetMapping("/greetings/{id}")
    Resource<Greeting> one(@PathVariable Long id) {

        Greeting greeting = repository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException(id));

        return assembler.toResource(greeting);
    }

    // Aggregate root
    /* getting aggregate mapping
     moving to REST
  @GetMapping("/greetings")
    List<Greeting> all() {
        return repository.findAll();
    }

 */
    @GetMapping("/greetings")
    Resources<Resource<Greeting>> all() {

        List<Resource<Greeting>> greetings = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(greetings,
                linkTo(methodOn(GreetingController.class).all()).withSelfRel());
    }

    @PostMapping("/greetings")
    ResponseEntity<?> newGreeting(@RequestBody Greeting newGreeting) throws URISyntaxException {
        Resource<Greeting> resource = assembler.toResource(repository.save(newGreeting));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    /* for new REST
    @PutMapping("/greetings/{id}")
    Greeting replaceGreeting(@RequestBody Greeting newGreeting, @PathVariable Long id) {

        return repository.findById(id)
                .map(greeting -> {
                    greeting.setGreet(newGreeting.getGreet());
                    greeting.setName(newGreeting.getName());
                    return repository.save(greeting);
                })
                .orElseGet(() -> {
                    newGreeting.setId(id);
                    return repository.save(newGreeting);
                });
    } */

    @PutMapping("/greetings/{id}")
    ResponseEntity<?> replaceGreeting(@RequestBody Greeting newGreeting, @PathVariable Long id) throws URISyntaxException {

        Greeting updatedGreeting = repository.findById(id)
                .map(greeting -> {
                    greeting.setGreet(newGreeting.getGreet());
                    greeting.setName(newGreeting.getName());
                    return repository.save(greeting);
                })
                .orElseGet(() -> {
                    newGreeting.setId(id);
                    return repository.save(newGreeting);
                });

        Resource<Greeting> resource = assembler.toResource(updatedGreeting);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    /* @DeleteMapping("/greetings/{id}")
    void deleteGreeting(@PathVariable Long id) {
        repository.deleteById(id);
    }*/

    @DeleteMapping("/greetings/{id}")
    ResponseEntity<?> deleteGreeting(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
