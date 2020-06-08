package com.springweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springweb.service.impl.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SpringWebConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * @Autowired private UserDetailsServiceImpl userDetailsService;
	 */

	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	@Autowired
	public void registerGlobalAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		authenticationManagerBuilder.authenticationProvider(authenticationProvider);
		/*
		 * authenticationManagerBuilder.ldapAuthentication()
		 * .userDnPatterns("uid={0},ou=people") .groupSearchBase("ou=groups")
		 * .contextSource() .url("ldap://localhost:8080/dc=test,dc=com") .and()
		 * .passwordCompare() .passwordEncoder(passwordEncoder())
		 * .passwordAttribute("userPassword");
		 */
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll()
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/list")
				.failureUrl("/login?error=true").permitAll().and()
				.logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll();
		//http.csrf().disable();
	}

}
