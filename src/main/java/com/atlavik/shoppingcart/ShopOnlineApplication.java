package com.atlavik.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@ComponentScan({"com.atlavik"})
public class ShopOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOnlineApplication.class, args);
	}

}
