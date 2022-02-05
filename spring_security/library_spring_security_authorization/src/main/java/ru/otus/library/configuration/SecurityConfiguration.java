package ru.otus.library.configuration;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.otus.library.services.UserService;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/edit/book", "/list/book", "/books").permitAll()
                .antMatchers("/delete/book/comment", "/add/book/comment").authenticated()
                .antMatchers("/delete/**", "/update/**").hasAnyRole("AUTHOR", "ADMIN")
                .antMatchers("/add/**").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .key("testKey")
                .tokenValiditySeconds(24*60*60)
                .and()
                .logout()
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
