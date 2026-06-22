package com.Daniel_Muntean4.pomodoro_api;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    JwtFilter jwtFilter;
    SecurityConfig(JwtFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
         httpSecurity.
                 cors(Customizer.withDefaults()).csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/token", "/swagger-ui/**", "/v3/api-docs/**" ,
                                "/swagger-ui.html", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/**").hasAuthority("READ")
                        .requestMatchers(HttpMethod.PATCH,"/api/**").hasAuthority("WRITE")
                        .requestMatchers(HttpMethod.PUT,"/api/**").hasAuthority("WRITE")
                        .requestMatchers(HttpMethod.POST,"/api/**").hasAuthority("WRITE")
                        .requestMatchers(HttpMethod.DELETE,"/api/**").hasAuthority("WRITE")
                        .anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;


    return  httpSecurity.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://daniel-muntean4.github.io", "http://localhost:5173"));
        config.setAllowedMethods(List.of("POST", "PUT", "PATCH", "DELETE", "GET", "OPTION"));
        config.setAllowedHeaders(List.of("Authorization", "Content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
            return  source;
    }
}
