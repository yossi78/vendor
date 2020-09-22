package com.example.learn.dl.repositories;

import com.example.learn.dl.dao.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface PersonRepository  extends JpaRepository<PersonEntity, Long> {

    @Query(nativeQuery=true, value="SELECT  * FROM  persons WHERE FIRST_NAME=:firstName AND AGE>:age ;")
    public Optional<List<PersonEntity>> getAllPersonByFirstNameAndAgeHigherThen(@Param("firstName") String firstName, @Param("age") Integer age);


    public Optional<List<PersonEntity>> findAllByFirstNameAndAge(@Param("firstName") String firstName, @Param("age") Integer age);



}
