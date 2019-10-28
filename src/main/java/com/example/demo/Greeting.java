package com.example.demo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Greeting{

    private @Id @GeneratedValue Long id;
    private String firstGreet;
    private String lastGreet;
    private String name;

    Greeting(String firstGreet, String lastGreet, String name) {
        this.lastGreet = lastGreet;
        this.firstGreet = firstGreet;
        this.name = name;
    }

    public String getGreet () {
        return this.firstGreet+" "+this.lastGreet;
    }

    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setGreet (String greet) {
        String[] parts = greet.split(" ");
        this.firstGreet = parts[0];
        this.lastGreet = parts[1];
    }

}

