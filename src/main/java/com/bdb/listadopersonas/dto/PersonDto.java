package com.bdb.listadopersonas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.util.Calendar;
import java.util.Date;
@Data
public class PersonDto {

    private Long id;
    @NotNull(message = "El dni no puede ser vacio") @Positive
    private Long dni;
    @NotNull @NotBlank(message = "El campo de nombre no puede ir vacio")
    private String name;
    @Past(message = "date of birth must be less than today")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birth;
    private Long father_dni;
    private Long mother_dni;
}
