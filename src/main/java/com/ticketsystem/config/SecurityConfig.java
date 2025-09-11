package com.ticketsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // thêm vào để có thể phân quyền = @PreAuthorized mà không cần phân quyền = endpoint

public class SecurityConfig {

    private final String[] PUBLIC_END_POINT_POST = {
            "/login","/register", "/auth/token", "/auth/introspect", "auth/logout", "auth/refresh"
    };
    private final String[] PUBLIC_END_POINT_GET = {
            "/login","/register","test"
    };

    @Autowired
    CustomJwtDecoder customJwtDecoder;

    @Autowired
    AuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                request ->
                        request
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                                .requestMatchers(HttpMethod.POST, PUBLIC_END_POINT_POST).permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_END_POINT_GET).permitAll()
                                .requestMatchers(HttpMethod.PUT).permitAll()
//                         .requestMatchers(HttpMethod.GET,"/users").hasRole(Role.ADMIN.name())
                                // thay vì phân quyền theo end point thì có thể dùng @Preauthorize #10
                                .anyRequest().authenticated());

        //thực hiện request mà cung cấp 1 token của user thì server sẽ xác thực người dùng
        //dựa trên token để cấp quyền truy cập
//            httpSecurity.oauth2ResourceServer(oauth2 ->
//                    oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
//                    );
        httpSecurity
                .addFilterBefore(new JwtSessionFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(customJwtDecoder)
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                                .authenticationEntryPoint(customAuthenticationEntryPoint) // config tất cả các endpoint
                        //k có quyền truy cập đều phải quay trở về form login

                );
        httpSecurity.cors(withDefaults());


        //         httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //tương tự dòng trên nhưng là dùng lambda
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
