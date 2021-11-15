package com.air.company.spring.config;

import com.air.company.spring.service.impls.AccountsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountsServiceImpl accountsService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/accounts/createUser").not().fullyAuthenticated()
                .antMatchers("/accounts/auth").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/accounts/makeAdmin").hasRole("ADMIN")
                .antMatchers("/planes/createPlane").hasRole("ADMIN")
                .antMatchers("/flights/createFlight").hasRole("ADMIN")
                //Доступ только для пользователей с ролью USER
                .antMatchers("/tickets/showMyTickets/**").hasRole("USER")
                .antMatchers("/flights/findFlight").hasRole("USER")
                .antMatchers("/createTicket").hasRole("USER")
                .antMatchers("/createTransferTicket").hasRole("USER")
                .antMatchers("/tickets/deactivate").hasRole("USER")
                .antMatchers("/planes/showPlane").hasRole("USER")
                .antMatchers("/seats/showSeats").hasRole("USER")
                //Доступ разрешен всем пользователей
                .antMatchers("/home").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
