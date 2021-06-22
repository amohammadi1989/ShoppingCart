package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.ShoppingCartDto;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.ShoppingCart;
import com.atlavik.shoppingcart.repository.ShoppingCartRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@SpringBootTest
class ShoppingCartServiceTest {
        @Autowired
        ShoppingCartService shoppingCartServiceTest;
        @MockBean
        ShoppingCartRepository shoppingCartRepository;
        @Autowired
        ModelMapper modelMapper;
        private ShoppingCartDto inputCart;
        @BeforeEach
        void setUp(){
                UserDto  inputUserDto=UserDto.builder()
                        .phone( UserConstants.PHONE_TEST )
                        .password(  UserConstants.PASS_TEST )
                        .userName( UserConstants.USERNAME_TEST )
                        .build();

                inputCart= ShoppingCartDto.builder()
                        .country( Country.Brazil  )
                        .currency( Currency.USD )
                        .totalPrice( 3.3 )
                        .user( inputUserDto )
                        .status( Status.OPENED )
                        .build();
        }
        @Test
        void itShouldFindCartByUserId(){
                //Given
                ShoppingCart shoppingCart=modelMapper.map( inputCart,ShoppingCart.class );
                BDDMockito.given(
                        shoppingCartRepository
                                .findShoppingCartByUserId( 1L ) )
                        .willReturn( Optional.of(shoppingCart ) );
                //When
                Optional<ShoppingCartDto> returnDto=shoppingCartServiceTest
                        .findCartByUserId( 1L );
                //Then
                Assertions.assertThat( returnDto ).isNotEmpty();
        }
        @Test
        void itShouldNullWhenFindCartByUserId(){
                //Given
                BDDMockito.given(
                        shoppingCartRepository
                                .findShoppingCartByUserId( 1L ) )
                        .willReturn( Optional.empty() );
                //When
                Optional<ShoppingCartDto> returnDto=shoppingCartServiceTest
                        .findCartByUserId( 1L );
                //Then
                Assertions.assertThat( returnDto ).isEmpty();
        }
        @Test
        void itShouldFindCartByCartIdAndUserName(){
                //Given
                ShoppingCart shoppingCart =modelMapper.map( inputCart,ShoppingCart.class );
                BDDMockito.given( shoppingCartRepository
                        .findShoppingCartByIdAndUser(
                                shoppingCart.getId()
                                ,UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.of( shoppingCart) );
                //When
                Optional<ShoppingCartDto> returnDto=shoppingCartServiceTest
                        .findCartByIdAndUserName( shoppingCart.getId(),UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( returnDto ).isNotEmpty();
        }
        @Test
        void itShouldEmptyWhenFindCartByCartIdAndUserName(){
                //Given
                BDDMockito.given( shoppingCartRepository
                        .findShoppingCartByIdAndUser(
                                1L
                                ,UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.empty() );
                //When
                Optional<ShoppingCartDto> returnDto=shoppingCartServiceTest
                        .findCartByIdAndUserName( 1L,UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( returnDto ).isEmpty();
        }
        @Test
        void itShouldFindCartByStatusAndUser(){
                //Given
                ShoppingCart shoppingCart=modelMapper
                        .map( inputCart,ShoppingCart.class );
                BDDMockito.given( shoppingCartRepository
                        .findShoppingCartByStatusAndUser( Status.OPENED.name(),UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.of( shoppingCart ) );
                //When
                Optional<ShoppingCartDto> returnDto= shoppingCartServiceTest
                        .findCartByStatusAndUser( Status.OPENED,UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( returnDto ).isNotEmpty();
        }
        @Test
        void itShouldEmptyWhenFindCartByStatusAndUser(){
                //Given
                BDDMockito.given( shoppingCartRepository
                        .findShoppingCartByStatusAndUser( Status.CLOSED.name(),UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.empty() );
                //When
                Optional<ShoppingCartDto> returnDto= shoppingCartServiceTest
                        .findCartByStatusAndUser( Status.OPENED,UserConstants.USERNAME_TEST );
                //Then
                Assertions.assertThat( returnDto ).isEmpty();
        }
        @Test
        void itShouldUpdateTotalPriceWithCartId(){
                //Given
                BDDMockito.given( shoppingCartRepository
                        .updatePriceWithCartId( 1L) ).willReturn( 1 );
                //When
               int updateInt= shoppingCartServiceTest.updateTotalPriceWithCartId( 1L );
                //Then
                Assert.assertEquals( 1,updateInt );
        }
        @Test
        void itShouldSaveCart(){
                //Given
                ShoppingCart saveCart=modelMapper.map( inputCart,ShoppingCart.class );
                BDDMockito.given( shoppingCartRepository.save( saveCart ) )
                        .willReturn( saveCart );
                //When
                ShoppingCartDto returnDto= shoppingCartServiceTest.addShoppingCart( inputCart );
                //Then
                Assertions.assertThat( returnDto ).isEqualTo( inputCart );
        }
        @Test
        void itShouldThrowExceptionWhenUserIsNull(){
                //Given
                ShoppingCart saveCart=modelMapper.map( inputCart,ShoppingCart.class );
                saveCart.setUser( null );
                BDDMockito.given( shoppingCartRepository.save( saveCart ) )
                        .willReturn( saveCart );
                //When
                //Then
                Assertions.assertThatThrownBy(
                        ()->shoppingCartServiceTest.addShoppingCart( inputCart )
                );
        }
}