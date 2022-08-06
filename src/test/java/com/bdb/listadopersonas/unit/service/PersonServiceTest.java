package com.bdb.listadopersonas.unit.service;

import com.bdb.listadopersonas.dto.DeleteResponse;
import com.bdb.listadopersonas.dto.PersonDto;
import com.bdb.listadopersonas.entity.Person;
import com.bdb.listadopersonas.exceptions.person.PersonAlreadyExist;
import com.bdb.listadopersonas.exceptions.person.PersonNotFoundException;
import com.bdb.listadopersonas.repository.PersonRepository;
import com.bdb.listadopersonas.service.PersonService;
import com.bdb.listadopersonas.util.TestUtilsGenerator;
import org.hibernate.sql.Delete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Spy
    ModelMapper modelMapper;

    @Mock
    PersonRepository personRepository;
    @InjectMocks
    PersonService personService;

    @Test
    public void getPersonList () throws ParseException {
        //arrange
        List<Person> myList = TestUtilsGenerator.getAllPersonsWithoutFathers();
        when(personRepository.findAll()).thenReturn(myList);
        List<PersonDto> myListDto = new ArrayList<>();
        for(Person person: myList) {
            myListDto.add(personService.mapToDto(person));
        }
        //act
        List<PersonDto> currentPerson = personService.getAllPerson();
        //assert
        verify(personRepository, atLeastOnce()).findAll();
        assertTrue(myListDto.equals(currentPerson));
    }
    @Test
    public void createPersonAlreadyExist () {
        //arrange
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        when(personRepository.existsByDni(Mockito.anyLong())).thenReturn(true);
        //assert
        assertThrows(PersonAlreadyExist.class,() -> personService.createPerson(personDto));
    }
    @Test
    public void createPersonWithFatherExist () throws ParseException {
        //arrange

        Person father = TestUtilsGenerator.generatorPerson();
        Optional<Person> opFather = Optional.of(father);
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        personDto.setName("Andres Florez");
        personDto.setBirth(TestUtilsGenerator.generateDate("1990-04-19"));
        personDto.setFather_dni(father.getDni());
        Person person =new Person();
        person.setFather(father);
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        person.setDni(personDto.getDni());
        when(personRepository.existsByDni(personDto.getDni())).thenReturn(false);
        when(personRepository.existsByDni(personDto.getFather_dni())).thenReturn(true);
        when(personRepository.findByDni(Mockito.anyLong())).thenReturn(opFather);
        when(personRepository.save(Mockito.any())).thenReturn(person);

        //act

        PersonDto currentPerson = personService.createPerson(personDto);
        //assert

        assertEquals(currentPerson, personDto);

    }

    @Test
    public void createPersonWithMotherExist () throws ParseException {
        //arrange

        Person mother = TestUtilsGenerator.generatorPerson();
        Optional<Person> opMother = Optional.of(mother);
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        personDto.setName("Andres Florez");
        personDto.setBirth(TestUtilsGenerator.generateDate("1990-04-19"));
        personDto.setMother_dni(mother.getDni());
        Person person =new Person();
        person.setMother(mother);
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        person.setDni(personDto.getDni());
        when(personRepository.existsByDni(personDto.getDni())).thenReturn(false);
        when(personRepository.existsByDni(personDto.getMother_dni())).thenReturn(true);
        when(personRepository.findByDni(Mockito.anyLong())).thenReturn(opMother);
        when(personRepository.save(Mockito.any())).thenReturn(person);

        //act

        PersonDto currentPerson = personService.createPerson(personDto);
        //assert

        assertEquals(currentPerson, personDto);

    }
    @Test
    public void personNotFound () {
        //arrange
        when(personRepository.existsById(Mockito.anyLong())).thenReturn(false);
        //assert
        assertThrows(PersonNotFoundException.class,() -> personService.getPersonById(14L));
    }

    @Test
    public void personExistById () {
        //arrange
        Person person = new Person();
        PersonDto personDto = new PersonDto();
        Optional<Person> opPerson = Optional.of(person);
        when(personRepository.existsById(Mockito.anyLong())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(opPerson);
        //act
        PersonDto currentPerson = personService.getPersonById(14L);
        //assert
        assertEquals( currentPerson , personDto);
    }
    @Test
    public void updatePersonAlreadyExist () {
        //arrange
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        when(personRepository.existsByDni(Mockito.anyLong())).thenReturn(true);
        //assert
        assertThrows(PersonAlreadyExist.class,() -> personService.updatePerson(personDto, 14L));
    }

    @Test
    public void updatePersonWithFatherExist () throws ParseException {
        //arrange

        Person father = TestUtilsGenerator.generatorPerson();
        Optional<Person> opFather = Optional.of(father);
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        personDto.setName("Andres Florez");
        personDto.setBirth(TestUtilsGenerator.generateDate("1990-04-19"));
        personDto.setFather_dni(father.getDni());
        Person person =new Person();
        person.setFather(father);
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        person.setDni(personDto.getDni());
        when(personRepository.getReferenceById(Mockito.anyLong())).thenReturn(person);
        when(personRepository.existsByDni(personDto.getDni())).thenReturn(false);
        when(personRepository.existsByDni(personDto.getFather_dni())).thenReturn(true);
        when(personRepository.findByDni(Mockito.anyLong())).thenReturn(opFather);
        when(personRepository.save(Mockito.any())).thenReturn(person);

        //act

        PersonDto currentPerson = personService.updatePerson(personDto, 14L);
        //assert

        assertEquals(currentPerson, personDto);

    }

    @Test
    public void updatePersonWithMotherExist () throws ParseException {
        //arrange

        Person mother = TestUtilsGenerator.generatorPerson();
        Optional<Person> opMother = Optional.of(mother);
        PersonDto personDto = new PersonDto();
        personDto.setDni(1000467445L);
        personDto.setName("Andres Florez");
        personDto.setBirth(TestUtilsGenerator.generateDate("1990-04-19"));
        personDto.setMother_dni(mother.getDni());
        Person person =new Person();
        person.setMother(mother);
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        person.setDni(personDto.getDni());
        when(personRepository.getReferenceById(Mockito.anyLong())).thenReturn(person);
        when(personRepository.existsByDni(personDto.getDni())).thenReturn(false);
        when(personRepository.existsByDni(personDto.getMother_dni())).thenReturn(true);
        when(personRepository.findByDni(Mockito.anyLong())).thenReturn(opMother);
        when(personRepository.save(Mockito.any())).thenReturn(person);

        //act

        PersonDto currentPerson = personService.updatePerson(personDto, 14L);
        //assert

        assertEquals(currentPerson, personDto);

    }
    @Test
    public void deletePerson () {
        //arrange
        DeleteResponse message = new DeleteResponse("Person successfully deleted");
        Person person = new Person();
        when(personRepository.getReferenceById(Mockito.anyLong())).thenReturn(person);
        doNothing().when(personRepository).delete(Mockito.any());
        //act
        DeleteResponse deleteResponse = personService.deletePerson(14L);
        //assert
        assertEquals(deleteResponse, message);

    }
}
