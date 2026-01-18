package com.softka.customer_service.model;


import com.softka.customer_service.model.dto.validation.CreateGroup;
import com.softka.customer_service.model.dto.validation.UpdateGroup;
import com.softka.customer_service.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TPERSONS")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "NAME",nullable = false)
    @Pattern(regexp = "^[A-Z\\s]+$", message = "El nombre no puede contener caracteres especiales")
    @NotNull(message = "El nombre es requerido")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "AGE")
    @Min(value =18, message = "El valor mínimo permitido es 18")
    @Max(value = 150,message = "El valor máximo permitido es 150")
    private Integer age;

    @Column(name = "DNI", unique = true, nullable = false)
    @NotNull(message = "La identificación es requerida")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^\\d{10}$",message = "La identificación debe ser de 10 caracteres")
    private String dni;

    @Column(name = "ADDRESS",nullable = false)
    private String address;

    @Column(name = "PHONE",nullable = false)
    @Pattern(regexp = "[0-9]+", message = "El teléfono solo debe contener numeros")
    @Size(min = 7, max = 10, message = "El teléfono no debe tener más de 10 caracteres")
    private String phone;


    @PrePersist
    @PreUpdate
    private void toUpperCase() {
        if (Objects.nonNull(name)) {
            name = name.toUpperCase();
        }
    }
}
