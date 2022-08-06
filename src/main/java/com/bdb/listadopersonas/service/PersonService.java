package com.bdb.listadopersonas.service;
import com.bdb.listadopersonas.dto.DeleteResponse;
import com.bdb.listadopersonas.dto.PersonDto;
import com.bdb.listadopersonas.entity.Person;
import com.bdb.listadopersonas.exceptions.person.PersonAlreadyExist;
import com.bdb.listadopersonas.exceptions.person.PersonNotFoundException;
import com.bdb.listadopersonas.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public List<PersonDto> getAllPerson() {
        return personRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PersonDto createPerson(PersonDto personDto){
        Person person = new Person();
        if(personRepository.existsByDni(personDto.getDni())){
            throw new PersonAlreadyExist();
        }
        person.setDni(personDto.getDni());
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        if(personDto.getFather_dni() != null){
            if (personRepository.existsByDni(personDto.getFather_dni())){
                person.setFather(personRepository.findByDni(personDto.getFather_dni()).get());
            }
        }
        if(personDto.getMother_dni() != null){
            if (personRepository.existsByDni(personDto.getMother_dni())) {
                person.setMother(personRepository.findByDni(personDto.getMother_dni()).get());
            }
        }
        return mapToDto(personRepository.save(person));
    }

    public PersonDto getPersonById(Long id) throws PersonNotFoundException {
        if(!personRepository.existsById(id)){
            throw new PersonNotFoundException();
        }
        return mapToDto(personRepository.findById(id).get());
    }

    public PersonDto updatePerson(PersonDto personDto, Long id){
        Person person = personRepository.getReferenceById(id);
        if(personRepository.existsByDni(personDto.getDni())){
            throw new PersonAlreadyExist();
        }
        person.setDni(personDto.getDni());
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        if(personDto.getFather_dni() != null){
            if (personRepository.existsByDni(personDto.getFather_dni())){
                person.setFather(personRepository.findByDni(personDto.getFather_dni()).get());
            }
        }else{
            person.setFather(null);
        }
        if(personDto.getMother_dni() != null){
            if (personRepository.existsByDni(personDto.getMother_dni())) {
                person.setMother(personRepository.findByDni(personDto.getMother_dni()).get());
            }
        }else{
            person.setMother(null);
        }
        personRepository.save(person);
        return mapToDto(person);
    }

    public DeleteResponse deletePerson(Long id){
        String message = "Person successfully deleted";
        DeleteResponse deleteResponse = new DeleteResponse(message);

        Person person = personRepository.getReferenceById(id);
        personRepository.delete(person);
        return deleteResponse;
    }
    public PersonDto mapToDto(Person person){

        PersonDto personDto = modelMapper.map(person,PersonDto.class);
        if(person.getFather() != null){
            personDto.setFather_dni(person.getFather().getDni());
        }
        if(person.getMother() != null){
            personDto.setMother_dni(person.getMother().getDni());
        }
        return personDto;
    }

}
