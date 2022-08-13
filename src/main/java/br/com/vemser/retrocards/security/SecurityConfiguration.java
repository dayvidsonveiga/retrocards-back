package br.com.vemser.retrocards.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz.antMatchers().permitAll()
                                .antMatchers("/usuario/ativar-desativar-usuario/{idUsuario}").hasRole("ADMIN")
                                .antMatchers("/usuario/cadastro-usuario").hasRole("ADMIN")
                                .antMatchers("/usuario/alterar-senha").hasAnyRole("ADMIN", "ALUNO", "PROFESSOR")
                                .antMatchers("/usuario/update").hasAnyRole("ADMIN", "ALUNO", "PROFESSOR")
                                .antMatchers("/usuario/logged").hasAnyRole("ADMIN", "ALUNO", "PROFESSOR")

                                .antMatchers(HttpMethod.GET, "/professor").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/professor").hasRole("PROFESSOR")
                                .antMatchers(HttpMethod.POST, "/professor").hasRole("PROFESSOR")
                                .antMatchers("/professor/logged").hasRole("PROFESSOR")
                                .antMatchers(HttpMethod.GET, "/professor/{idProfessor}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/professor/{idProfessor}").hasAnyRole("ADMIN")
                                .antMatchers("/professor/relatorio-maior-salario").hasRole("ADMIN")
                                .antMatchers("/professor/paginado").hasRole("ADMIN")
                                .antMatchers("/professor/byNome/{nome}").hasRole("ADMIN")

                                .antMatchers(HttpMethod.PUT, "/nota/{idNota}").hasRole("PROFESSOR")
                                .antMatchers(HttpMethod.GET, "/nota/{idAluno}").hasAnyRole("PROFESSOR", "ADMIN")
                                .antMatchers("/nota/aluno/logged").hasRole("ALUNO")

                                .antMatchers("/endereco").hasAnyRole("ALUNO", "PROFESSOR")
                                .antMatchers(HttpMethod.GET, "/endereco/{idEndereco}").hasAnyRole("ADMIN", "ALUNO", "PROFESSOR")
                                .antMatchers(HttpMethod.PUT, "/endereco/{idEndereco}").hasAnyRole("ALUNO", "PROFESSOR")
                                .antMatchers(HttpMethod.DELETE, "/endereco/{idEndereco}").hasAnyRole("ADMIN", "ALUNO", "PROFESSOR")

                                .antMatchers("/disciplina/**").hasRole("ADMIN")

                                .antMatchers("/curso/**").hasRole("ADMIN")

                                .antMatchers(HttpMethod.GET, "/aluno").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/aluno").hasRole("ALUNO")
                                .antMatchers(HttpMethod.POST, "/aluno").hasRole("ALUNO")
                                .antMatchers("/aluno/logged").hasRole("ALUNO")
                                .antMatchers(HttpMethod.GET, "/aluno/{idAluno}}").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/aluno/{idAluno}").hasRole("ADMIN")
                                .antMatchers("/aluno/paginado").hasAnyRole("ADMIN", "PROFESSOR")
                                .antMatchers("/aluno/completo").hasAnyRole("ADMIN", "PROFESSOR")
                                .antMatchers("/aluno/paginado").hasAnyRole("ADMIN", "PROFESSOR")
                                .anyRequest().authenticated()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/",
                "/usuario/login",
                "/usuario/recuperar-senha/{login}",
                "/user/register",
                "/user/login",
                "/user/logged",
                "/usuario/recuperar-senha/valid");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
