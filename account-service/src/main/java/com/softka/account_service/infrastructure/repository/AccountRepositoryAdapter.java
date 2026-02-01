package com.softka.account_service.infrastructure.repository;

import com.softka.account_service.application.port.out.AccountRepositoryPort;
import com.softka.account_service.domain.model.Account;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;

    public AccountRepositoryAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account create(Account accountDto) {
        return accountRepository.save(accountDto);
    }

    @Override
    public Account update(Account accountDto) {
        return accountRepository.save(accountDto);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }


}
