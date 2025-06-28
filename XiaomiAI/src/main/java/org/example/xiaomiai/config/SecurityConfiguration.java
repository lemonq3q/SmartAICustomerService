package org.example.xiaomiai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.xiaomiai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, exception)->{
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(401);
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "401");
            map.put("message", exception.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessEntryPoint(){
        return (request, response, authentication)->{
            response.setContentType("application/json;charset=utf-8");
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "0");
            map.put("message", "LOGIN SUCCESS");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureEntryPoint(){
        return (request, response, exception)->{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "401");
            map.put("message", exception.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication)->{
            response.setContentType("application/json;charset=utf-8");
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "0");
            map.put("message", "LOGOUT SUCCESS");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(authenticationEntryPoint())
            )
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/registered").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginProcessingUrl("/check_login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessEntryPoint())
                    .failureHandler(authenticationFailureEntryPoint())
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessHandler(logoutSuccessHandler())
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                System.out.println("username: " + username);
                String password = authentication.getCredentials().toString();
                System.out.println("password: " + password);
                UserDetails user = userService.loadUserByUsername(username);
                if(passwordEncoder.matches(password, user.getPassword())){
                    System.out.println("Access success: " + user.toString());
                    return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
                }
                else {
                    System.out.println("Access denied: The username or password is wrong!");
                    throw new BadCredentialsException("The username or password is wrong!");
                }

            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

}
