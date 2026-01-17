package com.softka.customer_service.model;


import com.softka.customer_service.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private int age;

    @Column(name = "DNI", unique = true, nullable = false)
    @NotNull(message = "La identificación es requerida")
    @Size(min = 10, max = 10, message = "La identificación no debe tener más de 10 caracteres")
    @Pattern(regexp = "^[0-9A-Z\\s]+$", message = "El nombre no puede contener caracteres especiales")
    private String dni;

    @Column(name = "ADDRESS",nullable = false)
    private String address;

    @Column(name = "PHONE",nullable = false)
    @Pattern(regexp = "[0-9]+", message = "El teléfono solo debe contener numeros")
    private String phone;


    @PrePersist
    @PreUpdate
    private void toUpperCase() {
        if (Objects.nonNull(name)) {
            name = name.toUpperCase();
        }
    }
}
