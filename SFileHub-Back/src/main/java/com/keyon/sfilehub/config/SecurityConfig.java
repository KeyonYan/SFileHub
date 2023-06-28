package com.keyon.sfilehub.config;

import com.alibaba.fastjson2.JSON;
import com.keyon.sfilehub.exception.BusinessException;
import com.keyon.sfilehub.exception.PermissionException;
import com.keyon.sfilehub.service.UserService;
import com.keyon.sfilehub.util.ResultUtil;
import com.keyon.sfilehub.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity(debug = true)	// 添加 security 过滤器
//@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证
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
     * DaoAuthenticationProvider 配置
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * 认证管理器配置
     * @return
     */
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(daoAuthenticationProvider());
    }


    @Bean
    public JsonLoginFilter jsonLoginFilter() throws Exception {
        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
        jsonLoginFilter.setFilterProcessesUrl("/login");
        jsonLoginFilter.setAuthenticationSuccessHandler((req, rsp, auth) -> {
            Object principal = auth.getPrincipal();
            rsp.setContentType("application/json;charset=utf-8");
            PrintWriter out = rsp.getWriter();
            out.write(JSON.toJSONString(ResultUtil.success(principal)));
            out.flush();
            out.close();
        });
        jsonLoginFilter.setAuthenticationFailureHandler((req, rsp, e) -> {
            rsp.setContentType("application/json;charset=utf-8");
            PrintWriter out = rsp.getWriter();
            out.write(JSON.toJSONString(ResultUtil.fail(ResultEnum.LOGIN_FAILED)));
            out.flush();
            out.close();
        });
        jsonLoginFilter.setAuthenticationManager(authenticationManager());
        return jsonLoginFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .addFilterAt(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> { // 未登录直接返回给前端JSON数据，而不是后端重定向
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSON.toJSONString(ResultUtil.fail(ResultEnum.LOGIN_REQUIRED)));
                    out.flush();
                    out.close();
                });
//                .rememberMe()
//                .tokenRepository(redisPersistentRe) // 配置token持久化方法
//                .rememberMeParameter("remember")
//                .rememberMeCookieName("remember-me-cookie-name") // 设置cookie名称
//                .userDetailsService(userService) // 设置userDetailsService
//                .tokenValiditySeconds(60*5); // cookie有效期: 5分钟
//                .and()
//                .formLogin().permitAll()
//                .successHandler((req, resp, auth) -> { // 登录成功直接返回给前端JSON数据，而不是后端重定向
//                    Object principal = auth.getPrincipal();
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write(JSON.toJSONString(ResultUtil.success(principal)));
//                    out.flush();
//                    out.close();
//                })
//                .failureHandler((req, resp, e) -> { // 登录失败直接返回给前端JSON数据，而不是后端重定向
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write(JSON.toJSONString(ResultUtil.fail("登录失败！")));
//                    out.flush();
//                    out.close();
//                })
//                .and()
//                .logout().permitAll()
////                .deleteCookies("remember-me-cookie-name") // 登出时清除cookie
////                .deleteCookies("JSESSIONID") // 登出时清除cookie
//                .and()
//                .and()
        return http.build();
    }


    /**
     * 配置跨源访问(CORS)
     * @return
     */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

}
