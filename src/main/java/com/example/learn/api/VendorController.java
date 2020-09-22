package com.example.learn.api;

import com.example.learn.dl.dao.PersonEntity;
import com.example.learn.dto.Person;
import com.example.learn.services.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/v1/vendors")
public class VendorController {


    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    private VendorService vendorService;


    @Autowired
    public VendorController(VendorService vendorService){
        this.vendorService = vendorService;
    }




    @GetMapping(value = "/redis")
    public ResponseEntity getAllPersonsFromRedis() {
        try {
            List<Person> persons = vendorService.getAllPersonsFromRedis();
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find all persons from Redis",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity getAllPersons() {
        try {
            List<Person> persons = vendorService.getAllPersons();
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find all persons",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping(value = "/health")
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("Person Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity addPerson(@RequestBody Person person) {
        try {
            PersonEntity personEntity = vendorService.addPerson(person);
            logger.info("Person has been added successfully");
            return new ResponseEntity(personEntity,HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new Person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable Long id) {
        try {
            logger.info("Get Person by Id: "+ id);
            Person person = vendorService.getPerson(id);
            if (person==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(person);
        }catch (Exception e){
            logger.error("Failed to find person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // MULTI QUERY PARAM
    @GetMapping(value = "/queryFilter")
    public ResponseEntity getAllPersonsByFirstNameAndAge(@RequestParam(value="firstName") String firstName,@RequestParam(value="age") Integer age) {
        try {
            List<Person> persons = vendorService.getAllPersonsByFirstNameAndAge(firstName,age);
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // MULTI PATH PARAM +  USE SQL QUERY
    @GetMapping(value = "/pathFilter/{firstName}/{age}")
    public ResponseEntity getAllPersonsByFirstNameAndAgeHigherThen(@PathVariable(value="firstName") String firstName,@PathVariable(value="age") Integer age) {
        try {
            List<Person> persons = vendorService.getAllPersonByFirstNameAndAgeHigherThen(firstName,age);
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find persons",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








    @PutMapping("/{id}")
    public ResponseEntity updatePerson(@PathVariable Long id , @RequestBody Person person) {
        try {
            logger.info("Update Person : "+ person);
            Boolean isUpdated = vendorService.updatePerson(person,id);
            if(!isUpdated){
                logger.warn("The person not exists and can not be updated");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to update person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) {
        try {
            logger.info("Delete Person by Id: "+ id);
            Boolean isRemoved = vendorService.removePerson(id);
            if(!isRemoved){
                logger.warn("The person not exists and can not be deleted");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to delete person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    




}

