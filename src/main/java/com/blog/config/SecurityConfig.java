package com.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JwtAuthenticationEntrypoint;
import com.blog.security.JwtAuthenticationFilter;

@Configuration // for declaring bean 
@EnableWebSecurity // for spring security
@EnableWebMvc  // for web-application and MVC design pattern
@EnableGlobalMethodSecurity(prePostEnabled = true) // security for role based API access
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public static final String[] PUBLIC_URls = {"/v3/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**", "/api/category/","/api/post/all/**", "/api/post/{postId}"};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		csrf().disable() // 
		.authorizeHttpRequests()
		.antMatchers("/api/auth/**").permitAll() // for public access to the url
		.antMatchers(PUBLIC_URls).permitAll()
		.anyRequest() // for authorizing any requests
		.authenticated().and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntrypoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	// implementing configure method -->
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService) // for userDetailService
		.passwordEncoder(passwordEncoder()); // defines how password will be stored in database  
	}
	
	// creating a bean of password-encoder to use it.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// this bean is created for login purpose
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
     // this below method is to handle CORS configuration -->
    @Bean
	public FilterRegistrationBean corseFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("common");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-110);
		return bean;
	}

}
