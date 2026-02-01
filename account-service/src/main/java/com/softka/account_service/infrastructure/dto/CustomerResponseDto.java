package com.softka.account_service.infrastructure.dto;

import com.softka.enums.Gender;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerResponseDto {

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
