package com.softka.customer_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@PrimaryKeyJoinColumn(name = "PERSON_ID")
@Entity
@Table(name = "TCLIENTS")
@NoArgsConstructor
public class Client extends Person {

    @Column(name = "PASSWORD", nullable = false)
    @NotNull(message = "La contraseña es requerida")
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
