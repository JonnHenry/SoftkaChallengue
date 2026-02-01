package com.softka.customer_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softka.customer_service.infrastructure.controller.ClientController;
import com.softka.customer_service.infrastructure.dto.ClientAccountDto;
import com.softka.customer_service.infrastructure.dto.ClientDto;
import com.softka.customer_service.application.port.in.ClientService;
import com.softka.enums.AccountType;
import com.softka.enums.Gender;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    // ==========================
    // Tests para ClientDto
    // ==========================
    @Test
    void testGetAllClients() throws Exception {
        List<ClientDto> clients = List.of(
                new ClientDto(1L, "0105476084", "Juan Perez", "ADFASFDSAD", Gender.M,20,"Test address", "072365656", Boolean.TRUE)

        );

        Mockito.when(clientService.getAll()).thenReturn(clients);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan Perez"))
                .andExpect(jsonPath("$[0].gender").value("M"));
    }

    @Test
    void testGetClientById() throws Exception {
        ClientDto client = new ClientDto(
                1L,
                "0105476084",
                "Juan Perez",
                "ADFASFDSAD",
                Gender.M,
                20,
                "Test address",
                "072365656",
                Boolean.TRUE
        );

        Mockito.when(clientService.getById(1L)).thenReturn(client);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.gender").value("M"));
    }

    @Test
    void testCreateClient() throws Exception {
        ClientDto client = new ClientDto(1L, "0105476084", "Juan Perez", "ADFASF01.asfdadsDSAD", Gender.M,20,"Test address", "072365656", Boolean.TRUE);

        Mockito.when(clientService.create(any(ClientDto.class))).thenReturn(client);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.gender").value("M"));
    }

    @Test
    void testUpdateClient() throws Exception {
        ClientDto client = new ClientDto(
                1L,
                "",
                "Juan Perez",
                "ADFASF01.asfdadsDSAD",
                Gender.M,
                20,
                "Test address",
                "072365656",
                Boolean.TRUE
        );

        Mockito.when(clientService.update(any(ClientDto.class))).thenReturn(client);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.gender").value("M"));
    }

    @Test
    void testDeleteClient() throws Exception {
        Mockito.doNothing().when(clientService).deleteById(1L);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateClientAccount_Success() throws Exception {
        ClientAccountDto account = new ClientAccountDto();
        account.setDni("0123456789");
        account.setName("Juan Perez");
        account.setPassword("Abcdef123.!");
        account.setGender(Gender.M);
        account.setAge(30);
        account.setAddress("Av. Prueba");
        account.setPhone("0987654321");
        account.setNumberAccount("0000000001");
        account.setAccountType(AccountType.Ahorro);
        account.setInitialAmount(100.0);
        account.setClientId(1L);

        Mockito.when(clientService.create(any(ClientAccountDto.class))).thenReturn(account);

        mockMvc.perform(post("/api/clientes/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dni").value("0123456789"))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.address").value("Av. Prueba"))
                .andExpect(jsonPath("$.phone").value("0987654321"))
                .andExpect(jsonPath("$.numberAccount").value("0000000001"))
                .andExpect(jsonPath("$.accountType").value("Ahorro"))
                .andExpect(jsonPath("$.initialAmount").value(100.0))
                .andExpect(jsonPath("$.clientId").value(1));
    }

    @Test
    void testCreateClientAccount_InvalidDni() throws Exception {
        ClientAccountDto account = new ClientAccountDto();
        account.setDni("123"); // inválido, debe ser 10 dígitos
        account.setName("Juan Perez");
        account.setPassword("Abcdef123!");
        account.setPhone("0987654321");
        account.setNumberAccount("0000000001");
        account.setAccountType(AccountType.Ahorro);
        account.setInitialAmount(100.0);
        account.setClientId(1L);

        mockMvc.perform(post("/api/clientes/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }
}