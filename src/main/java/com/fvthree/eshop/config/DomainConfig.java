package com.fvthree.eshop.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.fvthree.eshop")
@EnableJpaRepositories("com.fvthree.eshop")
@EnableTransactionManagement
public class DomainConfig {
}
