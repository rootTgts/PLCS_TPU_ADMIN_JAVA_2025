package com.wissen.tpu.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider tokenProvider;

	public SecurityConfig(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(tokenProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// 🔥 enable CORS using our corsConfigurationSource() bean
				.cors().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()

				// allow preflight for all paths
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				// login endpoint open
				.antMatchers("/plcstpu/verifier/signin").permitAll().antMatchers("/plcstpu/approver/signin").permitAll().antMatchers("/plcstpu/admin/signin").permitAll()
				//.antMatchers("/plcstpu/staging/verifier/signin").permitAll().antMatchers("/plcstpu/staging/approver/signin").permitAll().antMatchers("/plcstpu/staging/admin/signin").permitAll()

				// 🔒 secure your endpoints - MAKE SURE ROLES MATCH JWT
				.antMatchers("/plcstpu/verifier/**").hasAnyRole("verifier").antMatchers("/plcstpu/approver/**").hasAnyRole("approver").antMatchers("/plcstpu/admin/**").hasAnyRole("admin")
				//.antMatchers("/plcstpu/staging/verifier/**").hasAnyRole("verifier").antMatchers("/plcstpu/staging/approver/**").hasAnyRole("approver").antMatchers("/plcstpu/staging/admin/**").hasAnyRole("admin")
				
				.anyRequest().authenticated().and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// Angular dev origin
		//configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedOrigins(Arrays.asList("https://plcs.telangana.gov.in"));
		// or use setAllowedOriginPatterns(Arrays.asList("http://localhost:4200")) if
		// needed

		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
