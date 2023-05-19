package com.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {


        @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
        String issuerUri;
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authz -> authz.requestMatchers(HttpMethod.GET, "/products/**")
                            .hasAuthority("SCOPE_read")
                            .requestMatchers(HttpMethod.POST, "/products")
                            .hasAuthority("SCOPE_write")
                            .anyRequest()
                            .authenticated())
                            .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))));
            return http.build();
        }

}