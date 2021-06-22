package com.atlavik.shoppingcart.controller;

import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.repository.UserRepository;
import com.atlavik.shoppingcart.service.UserService;
import com.atlavik.shoppingcart.utils.JSON;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WithMockUser(
        username=UserConstants.USERNAME_TEST
        ,roles=UserConstants.USER_WRITE_PRIVILEGE )
class UserControllerTest {
        @MockBean
        UserService userService;
        @MockBean
        UserRepository userRepository;
        @Autowired
        MockMvc mockMvc;
        @Autowired
        ModelMapper modelMapper;
        @BeforeEach
         void setUp() {
        }
        @Test
        void itShouldSaveNewUser() throws Exception {
                //Given
	      UserDto userDto=UserDto.builder()
		    .userName( UserConstants.USERNAME_TEST )
		    .password( UserConstants.PASS_TEST )
		    .phone( UserConstants.PHONE_TEST )
		    .build();
	      BDDMockito.given( userService
		    .findUserByUserName( UserConstants.USERNAME_TEST ) )
		    .willReturn( Optional.empty());
	      String input=JSON.convertToJSON( userDto );
	      //When
	      ResultActions resultActions=this.mockMvc
		    .perform( MockMvcRequestBuilders.post( UserConstants.USER_CONTEXT )
			  .contentType( MediaType.APPLICATION_JSON ).content( input ) )
		    .andDo( MockMvcResultHandlers.print() );
	      //Then
	      resultActions
		    .andExpect( MockMvcResultMatchers.status().isOk() )
		    .andExpect( MockMvcResultMatchers.content()
			  .string( Matchers.containsString(UserConstants.USER_CREATEED) ) );
        }
        @Test
        void itShouldNotSaveNewUser()throws  Exception{
	      //Given
	      UserDto userDto=UserDto.builder()
		    .userName( UserConstants.USERNAME_TEST )
		    .password( UserConstants.PASS_TEST )
		    .phone( UserConstants.PHONE_TEST )
		    .build();
	      String input=JSON.convertToJSON( userDto );
	      BDDMockito.given( userService
		    .findUserByUserName( ArgumentMatchers.any()  ) )
		    .willReturn( Optional.of(userDto ));
	      //When
	      ResultActions resultActions=this.mockMvc
		    .perform( MockMvcRequestBuilders.post( UserConstants.USER_CONTEXT )
			  .contentType( MediaType.APPLICATION_JSON ).content( input ) )
		    .andDo( MockMvcResultHandlers.print() );
	      //Then
	      resultActions
		    .andExpect( MockMvcResultMatchers.status().isConflict() )
		    .andExpect( MockMvcResultMatchers.content()
			  .string( Matchers.containsString(UserConstants.USERNAME_CONFLICT) ) );
        }

}