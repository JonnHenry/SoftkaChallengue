package com.softka.account_service.service;

import com.softka.account_service.constants.AccountConstants;
import com.softka.account_service.exception.AlreadyExistException;
import com.softka.account_service.exception.NonExecutableTransactionExeption;
import com.softka.account_service.exception.NotFoundException;
import com.softka.account_service.mapper.TransactionMapper;
import com.softka.account_service.model.Account;
import com.softka.account_service.model.dto.BankStatementDto;
import com.softka.account_service.model.dto.TransactionDto;
import com.softka.account_service.repository.AccountRepository;
import com.softka.account_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ITransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public ITransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDto getById(Long id) {
        return transactionRepository.findById(id)
                .map(TransactionMapper.INSTANCE::toDTO).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        if (Objects.nonNull(transactionDto.getTransactionId()) && transactionRepository.findById(transactionDto.getTransactionId()).isPresent()) {
            throw new AlreadyExistException(
                    String.format(AccountConstants.TRANSACTION_ALREADY_EXIST, transactionDto.getTransactionId()));
        }

        Optional<Account> account = accountRepository.findById(transactionDto.getAccountId());
        if (account.isEmpty()){
            throw new NotFoundException(
                    String.format(AccountConstants.ACCOUNT_NOT_EXIST, transactionDto.getAccountId())
            );
        }
        double actualBalance;
        TransactionDto lastTransaction = this.getLastTransactionByAccountId(transactionDto.getAccountId());
        if (lastTransaction==null){
            actualBalance = account.get().getInitialAmount()+transactionDto.getAmount();
        }else{
            actualBalance = lastTransaction.getBalance()+transactionDto.getAmount();
        }
        if (actualBalance<0){
            throw new NonExecutableTransactionExeption(AccountConstants.TRANSACTION_NOT_EXECUTABLE);
        }
        transactionDto.setBalance(actualBalance);
        transactionDto.setTransactionDate(LocalDateTime.now());
        return TransactionMapper.INSTANCE.toDTO(
                transactionRepository.save(TransactionMapper.INSTANCE.toEntity(transactionDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<BankStatementDto> getAllBankStatementByClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
                                                                              Date dateTransactionEnd) {
        LocalDateTime from = dateTransactionStart.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime to = dateTransactionEnd.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .with(LocalTime.MAX);


        return transactionRepository.getAllByAccountClientIdAndDateBetween(clientId, from, to);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public TransactionDto getLastTransactionByAccountId(Long accountId) {
        return TransactionMapper.INSTANCE.toDTO(transactionRepository.getLastByAccountId(accountId));
    }
}
