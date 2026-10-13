package org.example.project3.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/customer/register").permitAll()
                        .requestMatchers("/api/v1/customer/update/{phoneNumber}","/api/v1/account/myAccounts", "/api/v1/account/create", "/api/v1/account/delete/{accountId}", "/api/v1/account/deposit/{accountId}/{amount}", "/api/v1/account/withdraw/{accountId}/{amount}", "/api/v1/account/transfer/{fromAccountId}/{toAccountId}/{amount}","/api/v1/customer/delete").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/auth/update","/api/v1/account/getById/{accountId}").hasAnyAuthority("ADMIN","CUSTOMER","EMPLOYEE")
                        .requestMatchers("/api/v1/auth/getUsers","/api/v1/employee/register", "/api/v1/employee/getEmployees","/api/v1/employee/update/{employeeId}","/api/v1/employee/delete/{employeeId}", "/api/v1/employee/getById/{employeeId}").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/customer/getCustomers", "/api/v1/customer/getById/{customerId}","/api/v1/account/getAccounts","/api/v1/account/activate/{accountId}", "/api/v1/account/block/{accountId}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)

                )
                .httpBasic(basic -> {})
                .build();
    }
}