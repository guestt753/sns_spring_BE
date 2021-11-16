package com.my.sns.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.my.sns.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired 
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired 
	private JwtEntryPoint jwtPoint;

	@Bean
	@Override 
	public AuthenticationManager authenticationManagerBean() throws Exception { 
		return super.authenticationManagerBean(); 
	}


    //   /webjars/** 경로에 대한 요청은 인증/인가 처리하지 않도록 무시합니다.
	
	@Override 
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers( "/webjars/**"); 
	}
	 
    
    // 인증 방법
    // customUserDetailsService객체에 인증 방법이 있으며 , 패스워드 인코더를 설정해둠
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }

    //   /, /main에 대한 요청은 누구나 할 수 있지만, 
//   그 외의 요청은 모두 인증 후 접근 가능합니다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
<<<<<<< HEAD
        .antMatchers("/", "/login.do/posts", "/download","/download2", "/reissue.do/posts", "/autologin.do/posts","/posts/post").permitAll()
=======
        .antMatchers("/", "/login.do/posts","/singup.do/posts", "/download", "/reissue.do/posts", "/autologin.do/posts", "/stomp-chat/**","/greeting", "/fcmtest.do").permitAll()
>>>>>>> b5ca04ae10957658703fcdcbce9b799bac999086
        .anyRequest().authenticated()
        .and()
        	.exceptionHandling()
        	.authenticationEntryPoint(jwtPoint)
//        .and()
//        	.sessionManagement()
//        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        	.addFilterBefore(
        			jwtAuthenticationFilter,
        			UsernamePasswordAuthenticationFilter.class
        	);
    
    }

    // 패스워드 인코더를 빈으로 등록합니다. 암호를 인코딩하거나, 
    // 인코딩된 암호와 사용자가 입력한 암호가 같은 지 확인할 때 사용합니다.
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
