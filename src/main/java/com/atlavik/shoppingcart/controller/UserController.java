package com.atlavik.shoppingcart.controller;

import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.ResponseDto;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By: Ali Mohammadi
 * Date: 13 Jun, 2021
 */
@RestController
@RequestMapping(UserConstants.USER_CONTEXT)
public class UserController {

        private UserService userService;

        public UserController(UserService userService) {
	      this.userService = userService;
        }
        @PostMapping
        public ResponseEntity<?> saveUser(@RequestBody UserDto userDto){
	      if(!userService.findUserByUserName( userDto.getUserName()).isPresent()){
		    return  new ResponseEntity<>(
			  new ResponseDto<>(UserConstants.USER_CREATEED,
				userService.saveUser(userDto)  ),
			  HttpStatus.OK);
	      }else
		    return new ResponseEntity<>(
			  new ResponseDto<>( UserConstants.USERNAME_CONFLICT ),
			  HttpStatus.CONFLICT );
        }
}
