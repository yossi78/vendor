package com.example.learn.services;

import com.example.learn.dl.dao.PersonEntity;
import com.example.learn.dl.repositories.PersonRepository;
import com.example.learn.dto.Person;
import com.example.learn.utils.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class VendorService {


    private RestTemplate restTemplate;
    private PersonRepository personRepository;


    @Autowired
    public VendorService(RestTemplate restTemplate, PersonRepository personRepository) {
        this.restTemplate = restTemplate;
        this.personRepository = personRepository;
    }

    public Person getPerson(Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElse(null);
        if (personEntity == null) {
            return null;
        }
        return personEntity.convertToDto();
    }


    public PersonEntity addPerson(Person person) {
        PersonEntity personEntity = person.convertToDao();
        personRepository.save(personEntity);
        return personEntity;
    }



    public void addPersons(List<PersonEntity> entities) {
        personRepository.saveAll(entities);
    }


    public Boolean updatePerson(Person updatedPerson,Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElse(null);
        if(personEntity==null){
            return false;
        }
        updatedPerson.setId(id);
        personRepository.save(updatedPerson.convertToDao());
        return true;

    }

    public Boolean removePerson(Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElse(null);
        if(personEntity==null){
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }


    public List<Person> getAllPersons() {
        List<PersonEntity> entities = personRepository.findAll();
        List<Person> results =entitiesToDtos(entities);
        return results;
    }


    public List<Person> getAllPersonsByFirstNameAndAge(String firstName,Integer age) {
        List<PersonEntity> entities = personRepository.findAllByFirstNameAndAge(firstName,age).orElse(null);
        List<Person> results = entitiesToDtos(entities);
        return results;
    }


    public List<Person> getAllPersonByFirstNameAndAgeHigherThen(String firstName,Integer age) {
        List<PersonEntity> entities = personRepository.getAllPersonByFirstNameAndAgeHigherThen(firstName,age).orElse(null);
        List<Person> results = entitiesToDtos(entities);
        return results;
    }


    public List<PersonEntity> fetchPersonsFromFiles(List<String> filePathes) throws IOException {
        List<PersonEntity> results = new ArrayList<>();
        for(String current:filePathes){
            List<Person> tempPersons = FileUtil.fileToListOfObjects(current,Person.class);
            results=mergePersonEntities(results,dtosToEntities(tempPersons));
        }
        return results;
    }



    public List<PersonEntity> fetchPersonsFromUrls(List<String> urls) {
       List<PersonEntity> entities = new ArrayList<>();
       for(String currentUrl:urls){
           List<PersonEntity> tempList = readPersonsFromUrl(currentUrl);
           entities = mergePersonEntities(entities,tempList);
       }
       return entities;
    }

    private List<PersonEntity> readPersonsFromUrl(String url) {
        List<PersonEntity> results  = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<PersonEntity[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, PersonEntity[].class);
        results = List.of(responseEntity.getBody());
        return results;
    }



    private List<PersonEntity> mergePersonEntities(List<PersonEntity> mainList,List<PersonEntity> tempList){
        List<PersonEntity> results = new ArrayList<>();
        mainList.stream().forEach(c->results.add(c));
        tempList.stream().forEach(c->results.add(c));
        return results;
    }


    private List<Person> entitiesToDtos(List<PersonEntity> entities){
        if(entities==null){
            return null;
        }
        List<Person> results = new ArrayList<>();
        entities.stream().forEach(current->results.add(current.convertToDto()));
        return results;
    }

    private List<PersonEntity> dtosToEntities(List<Person> dtos){
        List<PersonEntity> results = new ArrayList<>();
        if(dtos==null){
            return results;
        }
        for(Person current:dtos){
            PersonEntity personEntity = current.convertToDao();
            results.add(personEntity);
        }
        return results;
    }

    public Object parseStringToObject(String str,Class T) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object result = mapper.readValue(str,T);
        return result;
    }


    public List<Person> getAllPersonsFromRedis() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("URL_ONE","pinker",130));
        list.add(new Person("URL_TWO","king",140));
        list.add(new Person("URL_THREE","seller",150));
        list.add(new Person("URL_FOUR","momiker",160));
        return list;
    }
}



