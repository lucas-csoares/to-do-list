package com.todolist.config;

import com.todolist.security.filter.AutenticacaoFilter;
import com.todolist.security.filter.LoginFilter;
import com.todolist.security.permissoes.PermissaoEnum;
import com.todolist.service.security.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * Criptografa a senha do usuário
     * @return senha criptografada
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager ();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf (csrf -> csrf.disable ())
                .authorizeHttpRequests (auth -> {
                    auth.requestMatchers ("/login").permitAll ()
                            .requestMatchers (HttpMethod.POST, "/usuario").permitAll ()
                            .requestMatchers (HttpMethod.GET, "/usuario").hasAuthority (PermissaoEnum.ADMIN.toString ())
                            .anyRequest ()
                            .authenticated ();

                });


        httpSecurity.addFilterBefore (new LoginFilter ("/login",
                authenticationConfiguration.getAuthenticationManager ()), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore (new AutenticacaoFilter (), UsernamePasswordAuthenticationFilter.class);



        return httpSecurity.build ();
    }


}
