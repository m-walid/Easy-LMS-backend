package com.lilwel.elearning.Assignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AssignmentConfig {
    @Bean
    CommandLineRunner commandLineRunner(AssignmentRepository repo) {
        return args -> {
//            repo.save(
//            ));
        };

    }
}
