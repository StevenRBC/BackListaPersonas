package com.bdb.listadopersonas.util;

import com.bdb.listadopersonas.entity.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TestUtilsGenerator {

    public static Date generateDate (String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(date);
    }
    public static ArrayList<Person> getAllPersonsWithoutFathers() throws ParseException {
        ArrayList<Person> myList = new ArrayList<Person>();
        for (int i=0; i<=5; i++){
            Person person= new Person();

            person.setDni(1000467445L+i);
            person.setName("Steven Cuervo"+i);
            person.setBirth(generateDate("1999-01-10"));

        }
        return myList;
    }

    public static Person generatorPerson() throws ParseException {

        Person person = new Person();
        person.setName("Person test");
        person.setDni(123456789L);
        person.setBirth(generateDate("1920-04-09"));

        return person;
    }
}

