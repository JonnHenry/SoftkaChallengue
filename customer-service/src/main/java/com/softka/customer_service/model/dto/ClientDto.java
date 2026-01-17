package com.softka.customer_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softka.customer_service.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientDto {

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[A-Z\\s]+$")
    private String dni;

    @NotEmpty
    @Size(min = 5)
    private String name;

    @NotEmpty
    @Size(min = 10)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$\n",
            message = "En el campo de contraseña solo se permite ingresar un texto que contenga mínimo 10 caracteres, compuesto por letras (mayúsculas o minúsculas), números y al menos un carácter especial como !,@,#,$,%,&,*,_ y-"
    )
    private String password;

    private Gender gender;

    @Min(value = 1, message = "El valor mínimo permitido es 1")
    @Max(value = 150)
    private int age;

    private String address;

    @Size(min = 7, max = 10)
    @Pattern(regexp = "[0-9]+")
    private String phone;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "isActive")
    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
