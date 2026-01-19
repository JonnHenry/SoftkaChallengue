package com.softka.customer_service.model;

import com.softka.customer_service.model.dto.validation.CreateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@PrimaryKeyJoinColumn(name = "PERSON_ID")
@Entity
@Table(name = "TCLIENTS")
@NoArgsConstructor
public class Client extends Person {

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "La contraseña es requerida")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{10,}$",
            message = "En el campo de contraseña solo se permite ingresar un texto que contenga mínimo 10 caracteres, " +
                    "compuesto por letras (mayúsculas o minúsculas), números y al menos un carácter especial " +
                    "como !,@,#,$,%,&,*,_ y-")
    private String password;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive=isActive;
    }
}
