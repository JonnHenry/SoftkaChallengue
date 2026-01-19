package com.softka.account_service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import static org.assertj.core.api.Assertions.assertThat;
import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceApplicationTests {

    /*@MockBean
    private ConsumerFactory<String, String> consumerFactory;

    @MockBean
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;*/


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
        // Context
        assertThat(applicationContext).isNotNull();

        //Database
        assertThat(dataSource).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(jdbcTemplate).isNotNull();

        //Transactions
        assertThat(transactionManager).isNotNull();

        //Critical beans
        assertThat(applicationContext.containsBean("accountServiceImpl"))
                .isTrue();

        assertThat(applicationContext.containsBean("ITransactionServiceImpl"))
                .isTrue();

        assertThat(applicationContext.containsBean("accountRepository"))
                .isTrue();

        assertThat(applicationContext.containsBean("transactionRepository"))
                .isTrue();
	}

}
