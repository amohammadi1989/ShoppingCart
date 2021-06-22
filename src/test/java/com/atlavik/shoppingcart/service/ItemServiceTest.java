package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.ProductConstants;
import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.ItemsDto;
import com.atlavik.shoppingcart.dto.ProductDto;
import com.atlavik.shoppingcart.dto.ShoppingCartDto;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.enums.Category;
import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.ItemOrders;
import com.atlavik.shoppingcart.repository.ItemRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@SpringBootTest
class ItemServiceTest {
        @MockBean
        ItemRepository itemRepository;
        @Autowired
        ItemService itemServiceTest;
        @Autowired
        ModelMapper modelMapper;
        private ItemsDto itemInput;
        @BeforeEach
        void setUp() {
                UserDto inputUserDto=UserDto.builder()
                        .phone( UserConstants.PHONE_TEST )
                        .password(  UserConstants.PASS_TEST )
                        .userName( UserConstants.USERNAME_TEST )
                        .build();

                ShoppingCartDto shoppingCartDto= ShoppingCartDto.builder()
                        .country( Country.Brazil  )
                        .currency( Currency.USD )
                        .totalPrice( 3.3 )
                        .user( inputUserDto )
                        .status( Status.OPENED )
                        .build();
                ProductDto productDto= ProductDto.builder()
                        .category( Category.COMPUTER )
                        .name( ProductConstants.PRODUCT_NAME_TEST )
                        .price( ProductConstants.PRODUCT_PRICE_TEST )
                        .description( ProductConstants.PRODUCT_DESC_TEST )
                        .id( 1L )
                        .build();
                itemInput=ItemsDto.builder()
                        .product( productDto )
                        .shoppingCartDto( shoppingCartDto)
                        .build();
        }
        @Test
        void itShouldFindItemOrdersByParameters(){
                //Given
                ItemOrders itemOrders=modelMapper.map( itemInput,ItemOrders.class );
                BDDMockito.given( itemRepository
                        .findItemOrdersByParameters(1L,1L,UserConstants.USERNAME_TEST ) )
                        .willReturn( Optional.of(itemOrders) );
                //When
                Optional<ItemsDto> returnDto=itemServiceTest
                        .findItemOrdersByParameters( 1L,1L,UserConstants.USERNAME_TEST  );
                //Then
                Assert.assertNotNull( returnDto );
        }
        @Test
        void itShouldDeleteWithParameter(){
                //Given
                //When
                itemServiceTest.deleteByShoppingIdAndProductId( 1L );
                //Then
                BDDMockito.then( itemRepository ).should().deleteById( 1L );
        }
        @Test
        void itShouldAddProductToCart(){
                //Given
                ItemOrders itemOrders=modelMapper.map( itemInput,ItemOrders.class );
                BDDMockito.given( itemRepository.save( itemOrders) )
                        .willReturn( itemOrders );
                //When
                ItemsDto returnDto=itemServiceTest.addProductToCart( itemInput );
                //Then
                Assert.assertNotNull( returnDto );
        }
        @Test
        void itShouldSumTotalPrice(){
                //Given
                //When
                Double returnD=itemServiceTest.sumTotalPrice( Collections.singletonList(itemInput));
                //Then
                Assert.assertNotNull( returnD );


        }
}