package com.tistory.lky1001.boot2tutorial.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="/users")
public class WebTestController {

    @RequestMapping(value="/{user}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long user) {
        return new User();
    }

    @RequestMapping(value="/{user}/customers", method = RequestMethod.GET)
    public List<Customer> getUserCustomers(@PathVariable Long user) {
        return Arrays.asList(new Customer());
    }

    @RequestMapping(value="/{user}", method =RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long user) {
        return new User();
    }

    public static final class User {
        public int id;
    }

    public static final class Customer {

    }
}
