package com.softka.account_service.domain.service;

import com.softka.account_service.infrastructure.constants.AccountConstants;
import com.softka.account_service.domain.model.Account;
import com.softka.account_service.infrastructure.dto.AccountDto;
import com.softka.account_service.infrastructure.dto.CustomerResponseDto;
import com.softka.account_service.infrastructure.mapper.AccountMapper;
import com.softka.account_service.infrastructure.repository.AccountRepositoryAdapter;
import com.softka.account_service.infrastructure.utils.CustomerRestClient;
import com.softka.exception.AlreadyExistException;
import com.softka.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements com.softka.account_service.application.port.in.AccountService {

    private final AccountRepositoryAdapter accountRepositoryAdapter;

    private final CustomerRestClient customerRestClient;

    public AccountService(AccountRepositoryAdapter accountRepositoryAdapter, CustomerRestClient customerRestClient) {
        this.accountRepositoryAdapter = accountRepositoryAdapter;
        this.customerRestClient = customerRestClient;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> getAll() {
        return accountRepositoryAdapter.getAll()
                .stream()
                .map(AccountMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDto getById(Long id) {
        return accountRepositoryAdapter.findById(id)
                .map(AccountMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        Optional<CustomerResponseDto> clientResponse = customerRestClient.findClientById(accountDto.getClientId());
        clientResponse
                .filter(CustomerResponseDto::getIsActive)
                .orElseThrow(() -> new NotFoundException(AccountConstants.USER_ACCOUNT_NOT_EXIST));

        Optional.ofNullable(accountDto.getAccountId())
                .flatMap(accountRepositoryAdapter::findById)
                .ifPresent(a -> {
                    throw new AlreadyExistException(
                            String.format(AccountConstants.ACCOUNT_ALREADY_EXIST, a.getAccountId()));
                });

        Optional.ofNullable(accountDto.getNumber())
                .flatMap(accountRepositoryAdapter::findByNumber)
                .ifPresent(a -> {
                    throw new AlreadyExistException(
                            String.format(AccountConstants.ACCOUNT_NUMBER_ALREADY_EXIST, a.getNumber()));
                });


        accountDto.setIsActive(true);
        return AccountMapper.INSTANCE.toDTO(
                accountRepositoryAdapter.create(AccountMapper.INSTANCE.toEntity(accountDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public AccountDto update(AccountDto accountDto) {
        Optional<Account> accountFound = accountRepositoryAdapter.findById(accountDto.getAccountId());

        accountFound
                .filter(account -> account.getAccountId() != null)
                .orElseThrow(() -> new NotFoundException(String.format(AccountConstants.ACCOUNT_NOT_EXIST,
                        accountDto.getAccountId())));

        if (Objects.nonNull(accountDto.getClientId())) {
            Optional<CustomerResponseDto> clientResponse = customerRestClient.findClientById(accountDto.getClientId());
            if (clientResponse.isEmpty()
                    || clientResponse.get().getIsActive()==false){
                throw new NotFoundException(AccountConstants.USER_ACCOUNT_NOT_EXIST);
            }
        }

        return AccountMapper.INSTANCE.toDTO(
                accountRepositoryAdapter.create(AccountMapper.INSTANCE.updateEntityFromDTO(accountDto,accountFound.get()))
        );

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
        accountRepositoryAdapter.deleteById(id);
    }
}
