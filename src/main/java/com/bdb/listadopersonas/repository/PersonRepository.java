package com.bdb.listadopersonas.repository;

import com.bdb.listadopersonas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Optional<Person> findByDni(Long dni);
    boolean existsByDni(Long dni);

}
