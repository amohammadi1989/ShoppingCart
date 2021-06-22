package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.model.User;
import com.atlavik.shoppingcart.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootTest
class UserServiceTest {
        @Autowired
        private UserService userServiceTest;
        @MockBean
        private UserRepository userRepository;
        @Autowired
        ModelMapper modelMapper;
        @MockBean
        BCryptPasswordEncoder bCryptPasswordEncoder;
        private UserDto inputDto;

        @BeforeEach
        void setUp(){
                inputDto=UserDto.builder()
                        .phone( UserConstants.PHONE_TEST )
                        .password(  UserConstants.PASS_TEST )
                        .userName( UserConstants.USERNAME_TEST )
                        .build();
        }
        @Test
        void itShouldSaveNewUser(){
                //Given
                User saveUser=modelMapper.map( inputDto,User.class );
                Mockito.when( bCryptPasswordEncoder
                        .encode( inputDto.getPassword() ) )
                        .thenReturn( UserConstants.PASS_TEST  );
                Mockito
                        .when(userRepository.save(saveUser) ).thenReturn( saveUser );
                //When
                UserDto returnDto= userServiceTest.saveUser( inputDto );
                //Then
                Assertions.assertThat( inputDto).isEqualTo( returnDto );
        }
        @Test
        void itShouldFindUserByUserName(){
                //Given
                User findUser=modelMapper.map( inputDto,User.class );
                BDDMockito.given( userRepository
                        .findUserByUserName( inputDto.getUserName() ) )
                        .willReturn( Optional.of(findUser) );
                //When
                Optional<UserDto> returnUser=userServiceTest
                        .findUserByUserName( inputDto.getUserName() );
                //Then
                Assertions.assertThat( returnUser )
                        .isNotEqualTo( Optional.empty() );
        }
        @Test
        void itShouldEmptyWhenUserNotFound(){
                //Given
                BDDMockito.given( userRepository
                        .findUserByUserName( UserConstants .USERNAME_TEST) )
                        .willReturn( Optional.empty() );
                //When
                Optional<UserDto> returnUser=userServiceTest
                        .findUserByUserName( UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( returnUser ).isEqualTo( Optional.empty() );
        }
        @Test
        void itShouldLoadUserByUserName(){
                //Given
                User findUser=modelMapper.map( inputDto,User.class );
                BDDMockito.given( userRepository
                        .findUserByUserName( UserConstants.USERNAME_TEST ) )
                        .willReturn(Optional.of( findUser));
                //When
                UserDetails details=
                        userServiceTest.loadUserByUsername( UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( details ).hasNoNullFieldsOrProperties();
        }
        @Test
        void itShouldThrowExceptionWhenLoadUserByUserName(){
                //Given
                BDDMockito.given( userRepository
                        .findUserByUserName( UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.empty() );
                //When
                //Then
                Assertions
                        .assertThatThrownBy( ()->userServiceTest.loadUserByUsername( UserConstants.USERNAME_TEST ) )
                        .isInstanceOf( UsernameNotFoundException.class )
                        .hasMessageContaining( UserConstants.USER_INVALID  );
        }
}