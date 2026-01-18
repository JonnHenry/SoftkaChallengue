package com.softka.customer_service.model;

import com.softka.customer_service.model.enums.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    private Validator validator;

    private Client buildValidClient() {
        Client client = new Client();
        client.setPassword("securePassword");
        client.setIsActive(true);
        client.setName("JUAN PEREZ");
        client.setGender(Gender.M);
        client.setAge(30);
        client.setDni("1234567890");
        client.setAddress("Av De Prueba");
        client.setPhone("0999999999");
        return client;
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidClient() {
        Client client = buildValidClient();
        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations).isEmpty();
        assertThat(client.getIsActive()).isTrue();
        assertThat(client.getName()).isEqualTo("JUAN PEREZ");
    }

    @Test
    void shouldFailWhenPasswordIsNull() {
        Client client = buildValidClient();
        client.setPassword(null);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getMessage().contains("La contraseña es requerida"));
    }

    @Test
    void shouldFailWhenNameHasSpecialCharacters() {
        Client client = buildValidClient();
        client.setName("Juan@123");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations)
                .anyMatch(v -> v.getMessage()
                        .contains("no puede contener caracteres especiales"));
    }

    @Test
    void shouldFailWhenDniHasInvalidLength() {
        Client client = buildValidClient();
        client.setDni("12345");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations)
                .anyMatch(v -> v.getMessage()
                        .contains("no debe tener más de 10 caracteres"));
    }

    @Test
    void shouldFailWhenPhoneContainsLetters() {
        Client client = buildValidClient();
        client.setPhone("09ABC12345");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertThat(violations)
                .anyMatch(v -> v.getMessage()
                        .contains("solo debe contener numeros"));
    }

    @Test
    void shouldAllowChangeIsActiveFlag() {
        Client client = buildValidClient();

        client.setIsActive(false);

        assertThat(client.getIsActive()).isFalse();
    }

}