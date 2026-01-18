package com.softka.customer_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import static org.assertj.core.api.Assertions.assertThat;
import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceApplicationTests {

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
        assertThat(applicationContext.containsBean("clientServiceImpl"))
                .isTrue();

        assertThat(applicationContext.containsBean("clientRepository"))
                .isTrue();
	}

}
