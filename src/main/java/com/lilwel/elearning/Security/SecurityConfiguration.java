package com.lilwel.elearning.Security;

import com.lilwel.elearning.Account.Account;
import com.lilwel.elearning.Account.AccountService;
import com.lilwel.elearning.filters.CustomAuthenticationFilter;
import com.lilwel.elearning.filters.CustomAuthorizationFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

import static com.lilwel.elearning.Account.Account.Role.INSTRUCTOR;
import static com.lilwel.elearning.Account.Account.Role.STUDENT;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.accountService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.csrf().disable();
        http.cors();
        http.authorizeRequests().antMatchers(POST,"/api/v1/auth/signup").permitAll();
        http.authorizeRequests().antMatchers(POST,"/api/v1/auth/login").permitAll();

        //Assignment routes
        http.authorizeRequests().antMatchers(DELETE,"/api/v1/assignments/{id}").hasAuthority(INSTRUCTOR.toString());
//        http.authorizeRequests().antMatchers(GET,"/api/v1/assignments/{id}/submissions").hasAuthority(INSTRUCTOR.toString());
        http.authorizeRequests().antMatchers(POST,"/api/v1/assignments/{id}/submissions").hasAuthority(STUDENT.toString());

        //submissions routes
        http.authorizeRequests().antMatchers(POST,"/api/v1/submissions/{id}/**").hasAuthority(INSTRUCTOR.toString());

        //courses routes
        http.authorizeRequests().antMatchers(POST,"/api/v1/courses").hasAuthority(INSTRUCTOR.toString());
        http.authorizeRequests().antMatchers(DELETE,"/api/v1/courses/{id}").hasAuthority(INSTRUCTOR.toString());
        http.authorizeRequests().antMatchers(POST,"/api/v1/courses/{id}/assignments").hasAuthority(INSTRUCTOR.toString());
        http.authorizeRequests().antMatchers(POST,"/api/v1/courses/{id}/students").hasAuthority(INSTRUCTOR.toString());
        http.authorizeRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests().antMatchers(GET,"/api/v1/courses/**").hasAnyAuthority("STUDENT",Role.INSTRUCTOR);
//        http.authorizeRequests().anyRequest().permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v1/auth/signup");
                web.ignoring().antMatchers("/api/v1/auth/login");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();

    }
}
