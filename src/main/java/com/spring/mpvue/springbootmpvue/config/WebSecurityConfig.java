package com.spring.mpvue.springbootmpvue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;

import java.util.List;


/**
 * Created by xh on 2018/12/20 9:46.
 *
 * @author wy
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<HeaderWriter> headerWriters;
    @Autowired
    private AuthenticationSuccessHandler MyAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler MyAuthenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler MyLogoutSuccessHandler;
    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;
    /**
     * 加解密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 配置管理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/fj/**").permitAll()
                .antMatchers("/loan/**").permitAll()
                .antMatchers("/param/**").permitAll()
                .antMatchers("/record/**").permitAll()
                .antMatchers("/address/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/webService/**").permitAll()
                .antMatchers("/api/wx/**").permitAll()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .successHandler(MyAuthenticationSuccessHandler)
                    .failureHandler(MyAuthenticationFailureHandler)
                    .and().logout()
                    .logoutSuccessHandler(MyLogoutSuccessHandler)
                    .and()
                        // 认证失败处理类
                        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                        .authorizeRequests()
                        .antMatchers("/house/**").authenticated()
                        .anyRequest()
                        .authenticated()
                        .and().csrf().disable();
        http.addFilter(new HeaderWriterFilter(headerWriters));
    }
}
