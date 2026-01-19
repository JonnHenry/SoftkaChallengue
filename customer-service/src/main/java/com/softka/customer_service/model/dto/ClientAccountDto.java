package com.softka.customer_service.model.dto;

import com.softka.customer_service.model.dto.validation.CreateGroup;
import com.softka.customer_service.model.dto.validation.UpdateGroup;
import com.softka.customer_service.model.enums.AccountType;
import com.softka.customer_service.model.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientAccountDto {
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
                    "compuesto por letras (mayúsculas o minúsculas), números y " +
                    "al menos un carácter especial como !,@,#,$,%,&,*,_ y-",
            groups = {CreateGroup.class, UpdateGroup.class}
    )
    private String password;

    private Gender gender;

    @Min(value =18, message = "El valor mínimo permitido es 18",groups = {CreateGroup.class, UpdateGroup.class})
    @Max(value = 150,message = "El valor máximo permitido es 150",groups = {CreateGroup.class, UpdateGroup.class})
    private Integer age;

    @NotEmpty(groups = {CreateGroup.class})
    private String address;

    @NotEmpty(groups = {CreateGroup.class})
    @Pattern(regexp = "^\\d{7,10}$",message = "El campo debe contener solo números y tener entre 7 y 10 dígitos",
            groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;

    @NotEmpty(groups = {CreateGroup.class},message = "El número de cuenta no debe de ser vacio")
    @Pattern(regexp = "\\d+", message = "El número de cuenta debe de ser numerico")
    private String numberAccount;

    @NotNull(groups = {CreateGroup.class})
    private AccountType accountType;

    @Min(value = 0,message = "El valor del monto inicial debe de ser mayor a 0",
            groups = {CreateGroup.class, UpdateGroup.class})
    private double initialAmount;

    @NotNull(message = "El id del cliente no puede ser nulo",groups = {CreateGroup.class})
    private Long clientId;

}
