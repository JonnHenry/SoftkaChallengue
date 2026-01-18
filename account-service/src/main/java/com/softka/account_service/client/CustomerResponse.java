package com.softka.account_service.client;

import com.softka.account_service.model.enums.Gender;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerResponse {

    @EqualsAndHashCode.Include
    private Long id;

    private String dni;

    private String name;

    private String password;

    private Gender gender;

    private int age;

    private String address;

    private String phone;

    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
