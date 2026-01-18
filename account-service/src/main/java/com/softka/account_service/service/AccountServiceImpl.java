package com.softka.account_service.service;

import com.softka.account_service.client.CustomerResponse;
import com.softka.account_service.constants.AccountConstants;
import com.softka.account_service.exception.AlreadyExistException;
import com.softka.account_service.exception.NotFoundException;
import com.softka.account_service.mapper.AccountMapper;
import com.softka.account_service.model.dto.AccountDto;
import com.softka.account_service.repository.AccountRepository;
import com.softka.account_service.utils.CustomerRestClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements  AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRestClient customerRestClient;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDto getById(Long id) {
        return accountRepository.findById(id)
                .map(AccountMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        Optional<CustomerResponse> clientResponse = customerRestClient.findClientById(accountDto.getClientId());
        if ( clientResponse.isPresent()
                || clientResponse.get().getIsActive()==false){
            throw new NotFoundException(AccountConstants.USER_ACCOUNT_NOT_EXIST);
        }

        if (Objects.nonNull(accountDto.getAccountId())
                && accountRepository.findById(accountDto.getAccountId()).isPresent()) {
            throw new AlreadyExistException(
                    String.format(AccountConstants.ACCOUNT_ALREADY_EXIST,accountDto.getAccountId()));
        }
        accountDto.setIsActive(true);
        return AccountMapper.INSTANCE.toDTO(
                accountRepository.save(AccountMapper.INSTANCE.toEntity(accountDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public AccountDto update(AccountDto accountDto) {
        if (getById(accountDto.getAccountId())==null) {
            throw new NotFoundException(String.format(AccountConstants.ACCOUNT_NOT_EXIST,accountDto.getAccountId()));
        }

        Optional<CustomerResponse> clientResponse = customerRestClient.findClientById(accountDto.getClientId());
        if (clientResponse.isEmpty()
                || clientResponse.get().getIsActive()==false){
            throw new NotFoundException(AccountConstants.USER_ACCOUNT_NOT_EXIST);
        }

        return AccountMapper.INSTANCE.toDTO(accountRepository.save(AccountMapper.INSTANCE.toEntity(accountDto)));

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (getById(id)==null) {
            throw new NotFoundException(AccountConstants.USER_ACCOUNT_NOT_EXIST);
        }
        accountRepository.deleteById(id);
    }
}
