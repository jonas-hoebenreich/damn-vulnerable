package com.example.issues.configuration;

import com.example.issues.security.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsServiceImpl;


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private CsrfTokenRepository customCsrfTokenRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.cors().and().csrf().disable()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()

				//.anyRequest().authenticated().and()
				//.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/
		;
		http.csrf().csrfTokenRepository(customCsrfTokenRepository)
				.ignoringAntMatchers("/actuator/shutdown")
				.and()
				.formLogin()
				.and()
				//.httpBasic()
				//.and()
				.authorizeRequests()
				.antMatchers("/register", "/login").permitAll()
				.antMatchers("/secure/admin/users").hasAuthority("ADMIN")
				//.anyRequest().authenticated()
				;

	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}

}
