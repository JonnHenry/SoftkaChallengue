package com.softka.account_service.service;

import com.softka.account_service.application.port.in.TransactionService;
import com.softka.account_service.infrastructure.mapper.TransactionMapper;
import com.softka.account_service.domain.model.Account;
import com.softka.account_service.domain.model.Transaction;
import com.softka.account_service.infrastructure.dto.TransactionDto;
import com.softka.account_service.domain.model.enums.TransactionType;
import com.softka.account_service.infrastructure.repository.AccountRepository;
import com.softka.account_service.infrastructure.repository.TransactionRepository;
import com.softka.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TransactionServiceTest {

    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void create() {
        //Arrange
        Account account = new Account();
        account.setNumber("123456789");
        account.setAccountType(AccountType.Ahorro);
        account.setInitialAmount(1000);
        account.setIsActive(true);
        account.setClientId(1L);
        account.setIsActive(true);

        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(savedAccount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.Retiro);
        transaction.setAmount(500);
        transaction.setBalance(1500);
        transaction.setAccountId(savedAccount.getAccountId());

        //Act
        TransactionDto transactionCreated = transactionService.create(TransactionMapper.INSTANCE.toDTO(transaction));

        Transaction transactionFound =
                transactionRepository.findById(transactionCreated.getTransactionId()).get();

        // ASSERT
        assertThat(transactionFound.getAmount()).isEqualTo(500);
        assertThat(transactionFound.getTransactionType())
                .isEqualTo(TransactionType.Retiro);
    }

}