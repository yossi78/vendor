package com.example.learn.dl.dao;

import com.example.learn.dto.Person;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity(name="PersonEntity")
@Table(name = "persons")
@Data
public class PersonEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;


    @Column(name = "LAST_NAME")
    private String lastName;


    @Column(name = "AGE")
    private Integer age;

    public PersonEntity() {
    }

    public PersonEntity(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person convertToDto(){
        Person person = new Person(this.id,this.firstName,this.lastName,this.age);
        return person;
    }

}
