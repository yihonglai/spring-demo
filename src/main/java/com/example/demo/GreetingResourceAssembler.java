package com.example.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class GreetingResourceAssembler implements ResourceAssembler<Greeting, Resource<Greeting>> {

    @Override
    public Resource<Greeting> toResource(Greeting greeting) {

        return new Resource<>(greeting,
                linkTo(methodOn(GreetingController.class).one(greeting.getId())).withSelfRel(),
                linkTo(methodOn(GreetingController.class).all()).withRel("greetings"));
    }
}
