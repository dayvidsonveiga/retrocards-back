package br.com.vemser.retrocards.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                        authz.antMatchers("/", "/user/login", "/user/create").permitAll()
                                // User
                                .antMatchers("/user/change-role/{idUser}").hasRole("ADMIN")
                                .antMatchers("/user/list").hasAnyRole("FACILITATOR", "ADMIN")
                                .antMatchers("/user/list-name-email").hasAnyRole("FACILITATOR", "MEMBER", "ADMIN")
                                .antMatchers("/user/get-logged").hasAnyRole("FACILITATOR", "MEMBER", "ADMIN")

                                // Sprints
                                .antMatchers("/sprint/delete/{idSprint}").hasRole("FACILITATOR")
                                .antMatchers("/sprint/update/{idSprint}").hasRole("FACILITATOR")
                                .antMatchers("/sprint/check-progress/{idSprint}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/sprint/create").hasAnyRole("FACILITATOR")
                                .antMatchers("/sprint/list").hasAnyRole("FACILITATOR", "MEMBER")

                                // Retrospective
                                .antMatchers("/retrospective/list/sprint/{idSprint}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/retrospective/delete/{idRetrospective}").hasRole("FACILITATOR")
                                .antMatchers("/retrospective/list/{idRetrospective}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/retrospective/update/{idRetrospective}").hasRole("FACILITATOR")
                                .antMatchers("/retrospective/update-status/{idRetrospective}").hasRole("FACILITATOR")
                                .antMatchers("/retrospective/create").hasRole("FACILITATOR")

                                // Kudo Cards
                                .antMatchers("/kudocard/delete/{idKudocard}").hasRole("MEMBER")
                                .antMatchers("/kudocard/update/{idKudoCard}").hasRole("MEMBER")
                                .antMatchers("/kudocard/list/{idKudoBox}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/kudocard/list/kudocards/{idKudoBox}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/kudocard/create").hasRole("MEMBER")

                                // Kudo Box
                                .antMatchers("/kudobox/list/sprint/{idSprint}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/kudobox/delete/{idKudoBox}").hasRole("FACILITATOR")
                                .antMatchers("/kudobox/update/{idKudoBox}").hasRole("FACILITATOR")
                                .antMatchers("/kudobox/list/{idKudoBox}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/kudobox/create").hasRole("FACILITATOR")

                                // Item Retrospective
                                .antMatchers("/itemretrospective/list/retrospective/{idRetrospective}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/itemretrospective/delete/{idItem}").hasRole("MEMBER")
                                .antMatchers("/itemretrospective/update/{idItem}").hasRole("MEMBER")
                                .antMatchers("/itemretrospective/list/{idItemRetrospective}").hasAnyRole("FACILITATOR", "MEMBER")
                                .antMatchers("/itemretrospective/create").hasRole("MEMBER")

                                // Email
                                .antMatchers("/email/send").hasRole("FACILITATOR")
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**"
        );
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