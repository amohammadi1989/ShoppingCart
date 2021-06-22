package com.atlavik.shoppingcart.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created By: Ali Mohammadi
 * Date: 14 Jun, 2021
 */
public class SecurityContext {
        public  static String getUserName(){

	      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	      //FIXED: remove comment in production
	      return authentication.getName();

        }
}
