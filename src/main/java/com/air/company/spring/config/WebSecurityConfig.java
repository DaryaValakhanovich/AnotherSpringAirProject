package com.air.company.spring.config;

import com.air.company.spring.service.defalt.DefaultAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DefaultAccountsService accountsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/accounts/createUser").not().fullyAuthenticated()
                //  .antMatchers("/home").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/makeAdmin").hasRole("ADMIN")
                .antMatchers("/createPlane").hasRole("ADMIN")
                .antMatchers("/createFlight").hasRole("ADMIN")
                //Доступ только для пользователей с ролью USER
                .antMatchers("/news").hasRole("USER")
                .antMatchers("/showMyTickets").hasRole("USER")
                .antMatchers("/findFlight").hasRole("USER")
                .antMatchers("/chooseDifficultWay").hasRole("USER")
                .antMatchers("/chooseFlight").hasRole("USER")
                .antMatchers("/createTicket").hasRole("USER")
                .antMatchers("/createTransferTicket").hasRole("USER")
                .antMatchers("/deactivate").hasRole("USER")
                .antMatchers("/showPlane").hasRole("USER")
                .antMatchers("/showSeats").hasRole("USER")
                //Доступ разрешен всем пользователей
                .antMatchers("/", "/resources/**").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
