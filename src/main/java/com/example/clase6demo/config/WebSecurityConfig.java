package com.example.clase6demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().loginPage("/loginForm").loginProcessingUrl("/processLogin")
        .usernameParameter("correo").defaultSuccessUrl("/redirectByRole",true);

        http.authorizeRequests()
                .antMatchers("/employee","/employee/**").hasAnyAuthority("admin","logistica")
                .antMatchers("/shipper","/shipper/**").hasAuthority("admin")
                .anyRequest().permitAll();

        http.logout();
    }

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email, pwd, activo FROM usuario WHERE email = ?")
                .authoritiesByUsernameQuery("select u.email, r.nombre from usuario u inner join " +
                        "rol r on (u.idrol = r.idrol) where u.email = ? and u.activo = 1");
    }
}
