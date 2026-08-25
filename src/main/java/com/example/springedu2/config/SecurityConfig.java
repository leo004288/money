package com.example.springedu2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// SpringSecurity 설정
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 실수는 설정, 공부 설정안함
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/","/index.html",
                            "/css/**", "/img/**", "/js/**", "/fonts/**",
                            "/login", "/members/register"
                    ).permitAll() // 로그인 없이 사용가능.
                    .requestMatchers("/admin/**", "/vupdate", "/vdelete").hasRole("ADMIN")
                    .requestMatchers(
                            "/visitorMain.html", "/visitorFORM.html",
                            "/vlist", "/vinsert", "/vsearch", "/one",
                            "/members/me"
                    ).authenticated() // 로그인이 필요
                    .anyRequest().authenticated() // 설정하지 않은 다른 요청도 로그인 필요
            )
            .formLogin(form->form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")  // 생략가능
                    .defaultSuccessUrl("/visitorMain", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"))
            .exceptionHandling(
                    exception ->
                            exception.accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
