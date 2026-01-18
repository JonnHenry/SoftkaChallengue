package com.softka.account_service.service;

import com.softka.account_service.model.dto.AccountDto;
import java.util.List;

public interface AccountService {

    /**
     Get all accounts
     @return List<AccountDto>
     */
    public List<AccountDto> getAll();

    /**
     Get an account by id
     @param id
     @return AccountDto
     */
    public AccountDto getById(Long id);

    /**
     Create an account
     @param accountDto
     @return AccountDto
     */
    public AccountDto create(AccountDto accountDto);

    /**
     Update an account
     @param accountDto
     @return AccountDto
     */
    public AccountDto update(AccountDto accountDto);

    /**
     Delete an account
     @param id
     */
    public void deleteById(Long id);
}
