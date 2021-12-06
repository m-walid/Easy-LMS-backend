package com.lilwel.elearning.Account;
import com.lilwel.elearning.Security.AuthUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j @AllArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findFirstByEmail(username);
        if(account==null){
          log.error("user not found");
          throw new UsernameNotFoundException("user not found");
        }
        AuthUser user = new AuthUser(account.getId(),account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority(account.getRole().toString())));
        return user;

    }

    public Account loadAccountByEmail(String username){
        return accountRepository.findFirstByEmail(username);
    }

    public Account saveAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
}
