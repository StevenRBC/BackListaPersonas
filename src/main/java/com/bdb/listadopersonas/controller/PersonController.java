package com.bdb.listadopersonas.controller;

import com.bdb.listadopersonas.dto.DeleteResponse;
import com.bdb.listadopersonas.dto.PersonDto;
import com.bdb.listadopersonas.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/list")
    public List<PersonDto> getAllPerson(){
        return personService.getAllPerson();
    }

    @PostMapping("/create")
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid PersonDto personDto){
        return new ResponseEntity(personService.createPerson(personDto), HttpStatus.CREATED);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id){
        return new ResponseEntity(personService.getPersonById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody @Valid PersonDto personDto, @PathVariable Long id){
        return new ResponseEntity(personService.updatePerson(personDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteResponse>  deletePerson(@PathVariable Long id){
        return new ResponseEntity(personService.deletePerson(id), HttpStatus.OK);
    }
}
