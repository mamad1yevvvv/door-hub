package com.example.doorhub.security;

import lombok.RequiredArgsConstructor;
import org.example.doorhub.jwt.JwtAuthenticationEntryPoint;
import org.example.doorhub.jwt.JwtSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOauth2UserService oauth2UserDetails;

    final String[] MATCHERS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, JwtSecurityFilter jwtSecurityFilter) throws Exception {

        return security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers(MATCHERS)
                        .permitAll()
                        .anyRequest()
                        .fullyAuthenticated())
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(entryPoint -> entryPoint.authenticationEntryPoint(authenticationEntryPoint))
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .userInfoEndpoint(userInfoEndpointConfig
                                -> userInfoEndpointConfig.userService(oauth2UserDetails)))
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration localhost = new RedisStandaloneConfiguration("redis-19659.c322.us-east-1-2.ec2.cloud.redislabs.com", 19659);
        localhost.setUsername("default");
        localhost.setPassword("30nUG7ITRag7DbOHldaPALyx0j10M1gU");
        return new JedisConnectionFactory(localhost);
    }

}