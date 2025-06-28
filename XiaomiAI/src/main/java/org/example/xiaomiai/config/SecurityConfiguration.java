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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // 允许所有源
        configuration.setAllowedMethods(Arrays.asList("*"));  // 允许所有方法
        configuration.setAllowedHeaders(Arrays.asList("*"));  // 允许所有头
        configuration.setMaxAge(168000L);  // 预检请求缓存时间

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 应用到所有路径
        return source;
    }

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
            .cors(
                    cors-> cors.configurationSource(corsConfigurationSource())
            )
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .rememberMe(remember -> remember
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(86400)  // 24小时
            )
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(authenticationEntryPoint())
            )
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/user/registered","/check_login").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginProcessingUrl("/check_login")
                    .loginPage("/check_login")
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
            .authenticationProvider(authenticationProvider())
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
