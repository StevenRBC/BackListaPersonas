package com.bdb.listadopersonas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="data_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dni;
    private String name;
    private Date birth;
    @ManyToOne
    @JoinColumn(name = "father_id")
    private Person father;
    @ManyToOne
    @JoinColumn(name = "mother_id")
    private Person mother;
    @OneToMany(mappedBy = "father")
    private List<Person> childrenF;
    @OneToMany(mappedBy = "mother")
    private List<Person> childrenM;

}
