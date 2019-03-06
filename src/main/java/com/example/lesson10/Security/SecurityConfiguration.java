package com.example.lesson10.Security;

    import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http
                    .authorizeRequests()
                    .antMatchers("/","/customerForm/**", "/addQuantity/**","/processQuantity/**",
                            "/index/**", "/base/**")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/").permitAll().permitAll()
                    .and()
                    .httpBasic();
            http
                    .csrf().disable();
            http
                    .headers().frameOptions().disable();
        }

        @Bean
        public static BCryptPasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
            auth
                    .userDetailsService(userDetailsServiceBean()).passwordEncoder(encoder());
            auth.inMemoryAuthentication().withUser("admin")
                    .password(passwordEncoder().encode("password")).authorities
                    ("ADMIN");
        }

    }


