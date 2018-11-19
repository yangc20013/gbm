package com.yang.gbm.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class GbmWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/index.html", "/static/**", "/login");
		// web.ignoring().antMatchers("/sys/**");
	}

	@Bean
	public GbmUserPasswordAuthFilter gbmAuthFilter() throws Exception {
		GbmUserPasswordAuthFilter filter = new GbmUserPasswordAuthFilter();
		filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		filter.setAuthenticationManager(this.authenticationManager());
		filter.setAuthenticationSuccessHandler(new GbmLoginSuccessHandle());// 验证成功处理
		filter.setAuthenticationFailureHandler(new GbmLoginFailureHandle());// 验证失败处理
		filter.setUsernameParameter("username");
		filter.setPasswordParameter("password");
		return filter;
	}

	@Bean
	public GbmMetadataSource metadataSource() {
		GbmMetadataSource gbmMetadataSource = new GbmMetadataSource();
		return gbmMetadataSource;
	};

	@Bean
	public GbmUrlAccessDecisionManager urlAccessDecisionManager() {
		return new GbmUrlAccessDecisionManager();
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/")
		.and().authorizeRequests() 
		//设置忽略规则 
		.antMatchers("/static/**").permitAll()
		//设置拦截规则 
//		.anyRequest().authenticated() .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//				public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
//				fsi.setSecurityMetadataSource(metadataSource());
//				fsi.setAccessDecisionManager(urlAccessDecisionManager());
//				return fsi;
//			}
//		})
		.and().logout().permitAll() //注销行为任意访问
		.and().cors().and().csrf().disable();
		
		http.rememberMe();
		// session管理
		// session失效后跳转
		http.sessionManagement().invalidSessionUrl("/");
		// 只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
		http.sessionManagement().maximumSessions(1).expiredUrl("/");
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
		return passwordEncoder;
	}
//	public static void main(String[] args) {
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
//		System.out.println(passwordEncoder.encode("123456"));//$2a$04$i2QLaxYCmZXRLLcv7MNMU.ZpXQmso.lrEOQmldzKtTXuKI/OY4kA6
//	}

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetailsService userDetailsService = new GbmUserDetailsService();
		return userDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());// 密码加密
		authProvider.setUserDetailsService(userDetailsService());

		auth.authenticationProvider(authProvider);
	}
}
