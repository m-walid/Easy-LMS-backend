package com.lilwel.elearning;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Account.AccountRepository;
import com.lilwel.elearning.Account.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class ElearningApplication {

	public static void main(String[] args) {
		System.out.println((System.getenv().toString()));
		SpringApplication.run(ElearningApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(AccountService accountService){
//		return args -> {
//			accountService.saveAccount(Account.builder().name("walid").email("walid@walid.walid").password("walid1234").role(Account.Role.Teacher).id(UUID.randomUUID()).build());
//		};
//	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

}
