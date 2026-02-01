package com.softka.account_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.softka.account_service.dto.AccountDto;
import com.softka.account_service.service.IAccountService;
import com.softka.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAccountService IAccountService;

    @Test
    void testGetAll() throws Exception {
        List<AccountDto> accounts = List.of(
                new AccountDto(1L, "0000000001", AccountType.Ahorro, 100.0, true, 1L, null)
        );

        Mockito.when(IAccountService.getAll()).thenReturn(accounts);

        mockMvc.perform(get("/api/cuentas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("0000000001"))
                .andExpect(jsonPath("$[0].accountType").value("Ahorro"))
                .andExpect(jsonPath("$[0].initialAmount").value(100.0));
    }

    @Test
    void testGetById() throws Exception {
        AccountDto account = new AccountDto(1L, "0000000001", AccountType.Ahorro, 100.0, true, 1L, null);

        Mockito.when(IAccountService.getById(1L)).thenReturn(account);

        mockMvc.perform(get("/api/cuentas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("0000000001"))
                .andExpect(jsonPath("$.accountType").value("Ahorro"))
                .andExpect(jsonPath("$.initialAmount").value(100.0));
    }

    @Test
    void testCreate() throws Exception {
        AccountDto account = new AccountDto(1L, "0000000001", AccountType.Ahorro, 100.0, true, 1L, null);

        Mockito.when(IAccountService.create(any(AccountDto.class))).thenReturn(account);

        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value("0000000001"))
                .andExpect(jsonPath("$.accountType").value("Ahorro"))
                .andExpect(jsonPath("$.initialAmount").value(100.0));
    }

    @Test
    void testUpdate() throws Exception {
        AccountDto account = new AccountDto(1L, "0000000001", AccountType.Ahorro, 100.0, true, 1L, null);

        Mockito.when(IAccountService.update(any(AccountDto.class))).thenReturn(account);

        mockMvc.perform(put("/api/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("0000000001"))
                .andExpect(jsonPath("$.accountType").value("Ahorro"))
                .andExpect(jsonPath("$.initialAmount").value(100.0));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(IAccountService).deleteById(1L);

        mockMvc.perform(delete("/api/cuentas/1"))
                .andExpect(status().isNoContent());
    }


}