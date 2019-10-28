package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

interface GreetingRepository extends JpaRepository<Greeting, Long> {

}
