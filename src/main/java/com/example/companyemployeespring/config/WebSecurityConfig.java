package com.example.companyemployeespring.config;

import com.example.companyemployeespring.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/successLogin")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("ADMINISTRATOR")
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers("/president").hasAnyAuthority("PRESIDENT")
                .antMatchers("/employees")
                .hasAnyAuthority(",ADMINISTRATOR,PRESIDENT,VICE_PRESIDENT")
                .antMatchers("/sendMessage").authenticated()
                .antMatchers("/send").authenticated()
                .antMatchers("/loggedEmployee").authenticated()
                .antMatchers("/singleEmployeePage").authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
