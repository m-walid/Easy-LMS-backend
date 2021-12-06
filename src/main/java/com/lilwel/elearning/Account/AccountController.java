package com.lilwel.elearning.Account;

import com.lilwel.elearning.Handlers.ResponseHandler;
import com.lilwel.elearning.Security.AuthUser;
import com.lilwel.elearning.Security.AuthenticationRequest;
import com.lilwel.elearning.Security.AuthenticationResponse;
import com.lilwel.elearning.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/auth")
public class AccountController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest)  {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            final AuthUser userDetails = accountService.loadUserByUsername(authRequest.getUsername());
            final Account account = accountService.loadAccountByEmail(authRequest.getUsername());
            final String token = JwtUtil.generateToken(userDetails);
            final AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, account.getEmail(), account.getName(), account.getRole().toString(), account.getId().toString());
            return ResponseHandler.handleResponse("Successful login", HttpStatus.OK, authenticationResponse);
        } catch (BadCredentialsException e) {
            return ResponseHandler.handleResponse("wrong username or password", HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid Account account)  {
        try {
            Account addedAccount = accountService.saveAccount(account);
            addedAccount.setPassword(null);
            return ResponseHandler.handleResponse("Successfully registered", HttpStatus.OK, addedAccount);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseHandler.handleResponse("Successfully logged out", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.handleResponse("ERROR", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
