package com.softka.customer_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softka.customer_service.model.dto.validation.CreateGroup;
import com.softka.customer_service.model.dto.validation.UpdateGroup;
import com.softka.customer_service.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientDto {

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty(groups = {CreateGroup.class})
    @Pattern(regexp = "^\\d{10}$",message = "La identificación debe ser de 10 caracteres")
    private String dni;

    @NotEmpty(groups = {CreateGroup.class})
    @Size(min = 5,message = "El nombre no puede tener menos de 5 caracteres",
            groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    @NotEmpty(groups = {CreateGroup.class})
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$",
            message = "En el campo de contraseña solo se permite ingresar un texto que contenga mínimo 10 caracteres, " +
                    "compuesto por letras (mayúsculas o minúsculas), números y al menos un carácter especial " +
                    "como !,@,#,$,%,&,*,_ y-",
            groups = {CreateGroup.class,UpdateGroup.class}
    )
    private String password;

    private Gender gender;

    @Min(value =18, message = "El valor mínimo permitido es 18",groups = {CreateGroup.class,UpdateGroup.class})
    @Max(value = 150,message = "El valor máximo permitido es 150",groups = {CreateGroup.class, UpdateGroup.class})
    private Integer age;

    @NotEmpty(groups = {CreateGroup.class})
    private String address;

    @NotEmpty(groups = {CreateGroup.class})
    @NotEmpty(groups = {CreateGroup.class})
    @Pattern(regexp = "^\\d{7,10}$",message = "El campo debe contener solo números y tener entre 7 y 10 dígitos",
            groups = {CreateGroup.class, UpdateGroup.class})
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
