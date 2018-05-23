package com.excilys.formation.cdb.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/login*")
			);
	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);


	@Autowired
	TokenAuthenticationProvider provider;


	@Override
	protected void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(provider);
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().requestMatchers(PUBLIC_URLS);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {   http
		.sessionManagement()
		.sessionCreationPolicy(STATELESS)
		.and()
		.exceptionHandling()
		.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
		.and()
		.authenticationProvider(provider)
		.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()
		.logout().disable();
	}

	@Bean
	TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(successHandler());
		return filter;
	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new NoRedirectStrategy());
		return successHandler;
	}


	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(org.springframework.http.HttpStatus.UNAUTHORIZED);
	}


}
