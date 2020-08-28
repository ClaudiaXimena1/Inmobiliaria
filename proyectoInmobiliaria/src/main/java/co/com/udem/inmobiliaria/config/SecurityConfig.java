package co.com.udem.inmobiliaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import co.com.udem.inmobiliaria.security.jwt.JwtSecurityConfigurer;
import co.com.udem.inmobiliaria.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/auth/signin").permitAll()
				.antMatchers("/autenticar").permitAll()
				.antMatchers(HttpMethod.POST, "/users/addUser**").permitAll()
				.antMatchers(HttpMethod.POST, "/autenticar**").permitAll()
				.anyRequest().authenticated().and().apply(new JwtSecurityConfigurer(jwtTokenProvider));
		// @formatter:on

	}

}
