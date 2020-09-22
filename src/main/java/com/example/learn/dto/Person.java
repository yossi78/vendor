package com.example.learn.dto;
import com.example.learn.dl.dao.PersonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("age")
    private Integer age;

    public Person() {
    }

    public Person(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


    public Person(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public PersonEntity convertToDao(){
        PersonEntity personEntity = new PersonEntity(id,firstName,lastName,age);
        return personEntity;
    }

}

