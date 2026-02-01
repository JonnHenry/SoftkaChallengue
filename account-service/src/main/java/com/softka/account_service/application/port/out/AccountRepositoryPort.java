package com.softka.account_service.application.port.out;


import com.softka.account_service.domain.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {

    /**
     Get an account by id
     @param number
     @return Optional<Account>
     */
    Optional<Account> findByNumber(String number);


    /**
     Get an account by id
     @param id
     @return AccountDto
     */
    Optional<Account> findById(Long id);

    /**
     Get all accounts
     @return List<AccountDto>
     */
    List<Account> getAll();

    /**
     Get an account by id
     @param id
     @return AccountDto
     */
    Account getById(Long id);

    /**
     Create an account
     @param accountDto
     @return AccountDto
     */
    Account create(Account accountDto);

    /**
     Update an account
     @param accountDto
     @return AccountDto
     */
    Account update(Account accountDto);

    /**
     Delete an account
     @param id
     */
    void deleteById(Long id);
}
