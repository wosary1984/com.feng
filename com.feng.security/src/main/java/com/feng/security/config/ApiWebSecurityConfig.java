package com.feng.security.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.feng.security.service.CustomUserService;

@EnableWebSecurity
public class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String login = "/api/session/login";
	private final String logout = "/api/session/logout";

	@Autowired
	private CustomUserService loginUserDetailsService;

	@Autowired
	private CustomLoginHandler customLoginHandler;

	@Autowired
	private CustomLogoutHandler customLogoutHandler;

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	// @Autowired
	// private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		// auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
		// .withUser("feng").password("123456").roles("ADMIN").and().withUser("user").password("123456").roles("USER");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.cors();

		http.authorizeRequests()

				// below code doesn't work, because registered FilterSecurityInterceptor will
				// intercept all request URL
				// in MyAccessDecisionManager will released permitted URL

				// allow anonymous access access to Swagger docs
				.antMatchers("/v2/api-docs", "/**/swagger-ui.html", "/webjars/**", "/swagger-resources/**",
						"/configuration/**")
				.permitAll()
				// allow anonymous access treant resource, icon resource
				.antMatchers("/", "/index.html", "/resources/**", "/treant/**", "/icons/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				// allow anonymous check his session
				.antMatchers("/my/session").permitAll().antMatchers("/api/**").authenticated()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

		// this will ignore only h2-console csrf, spring security 4+
		http.csrf().ignoringAntMatchers("/h2-console/**");
		// this will allow frames with same origin which is much more safe
		http.headers().frameOptions().sameOrigin();

		http.formLogin();

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher(logout))
				// executed before logout
				.addLogoutHandler(customLogoutHandler)
				// executed after logout
				.logoutSuccessHandler(customLogoutHandler);

		http.exceptionHandling()
				// login user authtication error
				.accessDeniedHandler(customAccessDeniedHandler)
				// anonymous user authication error
				.authenticationEntryPoint(customAccessDeniedHandler);

		// ignore login csrf token check
		// ignore wechat in token check
		http.csrf().ignoringAntMatchers("/api/session/**", "/wechat/**");

		http.addFilterBefore(new AcceptHeaderLocaleFilter(), UsernamePasswordAuthenticationFilter.class);

		// replace default login form filter
		http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// bind csrf token to response header
		http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);

		// 拦截所有的请求进行定制的授权验证
		// http.addFilterBefore(myFilterSecurityInterceptor,
		// FilterSecurityInterceptor.class);
	}

	// @Override
	// public void configure(WebSecurity webSecurity) throws Exception
	// {
	// webSecurity
	// .ignoring()
	// .antMatchers("/resources/**").anyRequest();
	// }

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		logger.debug("register cors filter");
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		// configuration.setAllowedHeaders(Arrays.asList("authorization",
		// "content-type", "x-auth-token"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("X-CSRF-TOKEN"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(customLoginHandler);
		filter.setAuthenticationFailureHandler(customLoginHandler);
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl(login);
		return filter;
	}

	private static class AcceptHeaderLocaleFilter implements Filter {
		private AcceptHeaderLocaleResolver localeResolver;

		private AcceptHeaderLocaleFilter() {
			localeResolver = new AcceptHeaderLocaleResolver();
			localeResolver.setDefaultLocale(Locale.US);
		}

		@Override
		public void init(FilterConfig filterConfig) {
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			Locale locale = localeResolver.resolveLocale((HttpServletRequest) request);
			LocaleContextHolder.setLocale(locale);

			chain.doFilter(request, response);
		}

		@Override
		public void destroy() {
		}
	}

}
