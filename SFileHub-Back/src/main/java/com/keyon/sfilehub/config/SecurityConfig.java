package com.keyon.sfilehub.config;

import com.alibaba.fastjson2.JSON;
import com.keyon.sfilehub.service.UserService;
import com.keyon.sfilehub.util.ResultUtil;
import com.keyon.sfilehub.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity	// 添加 security 过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisPersistentRe redisPersistentRe;

    /**
     * 密码明文加密方式配置
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .tokenRepository(redisPersistentRe) // 配置token持久化方法
                .rememberMeParameter("remember")
                .rememberMeCookieName("remember-me-cookie-name") // 设置cookie名称
                .userDetailsService(userService) // 设置userDetailsService
                .tokenValiditySeconds(60) // cookie有效期
                .and()
                .formLogin()
                .successHandler((req, resp, auth) -> { // 登录成功直接返回给前端JSON数据，而不是后端重定向
                    Object principal = auth.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSON.toJSONString(ResultUtil.success(principal)));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> { // 登录失败直接返回给前端JSON数据，而不是后端重定向
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSON.toJSONString(ResultUtil.fail("登录失败！")));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> { // 未登录直接返回给前端JSON数据，而不是后端重定向
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSON.toJSONString(ResultUtil.fail("尚未登录，请先登录！")));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable();
        return http.build();
    }


    /**
     * 配置跨源访问(CORS)
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
