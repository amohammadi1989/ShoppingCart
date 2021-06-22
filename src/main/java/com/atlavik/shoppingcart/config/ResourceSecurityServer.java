package com.atlavik.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created By: Ali Mohammadi
 * Date: 20 Jun, 2021
 */
@Configuration
public class ResourceSecurityServer extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
	      http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/oauth/token").permitAll().
		    anyRequest().authenticated();
        }
}
