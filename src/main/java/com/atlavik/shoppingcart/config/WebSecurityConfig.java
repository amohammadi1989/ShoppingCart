package com.atlavik.shoppingcart.config;

import com.atlavik.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * Created By: Ali Mohammadi
 * Date: 13 Jun, 2021
*/
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private final UserService customDetailsService;

        public WebSecurityConfig(UserService customDetailsService ) {
	      this.customDetailsService = customDetailsService;
        }

        @Bean
        public PasswordEncoder encoder() {
	      return new BCryptPasswordEncoder( 12 );
        }

        @Override
        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth.userDetailsService(customDetailsService).passwordEncoder(encoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

	      http.authorizeRequests()
		    .antMatchers("/").permitAll()
		    .antMatchers("/h2-console/**").permitAll();
                http.cors().and().csrf().disable();

	  /*    http.authorizeRequests()
		    .anyRequest()
		    .authenticated()
		    .and()
		    .sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.NEVER);*/
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
	      CorsConfiguration configuration = new CorsConfiguration();
	      configuration.setAllowedOrigins(Collections.singletonList( "*" ));
	      configuration.setAllowedMethods(Collections.singletonList( "*" ));
	      configuration.setAllowedHeaders( Collections.singletonList( "*" ) );
	      configuration.setAllowCredentials(true);
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", configuration);
	      return source;
        }
        @Override
        public void configure(WebSecurity web)  {
	      web
		    .ignoring()
		    .antMatchers("/v2/api-docs",
			  "/configuration/ui",
			  "/swagger-resources/**",
			  "/configuration/security",
			  "/swagger-ui.html",
			  "/webjars/**")
		    .antMatchers( "/swagger**" )
		    .antMatchers( "/shopping/user**" )
		    .antMatchers("/h2-console/**");
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
        }

        @Bean
        public DaoAuthenticationProvider getAuthenticationProvider() {
	      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	      authenticationProvider.setUserDetailsService(customDetailsService);
	      authenticationProvider.setPasswordEncoder(encoder());

	      return authenticationProvider;
        }
}