package com.atlavik.shoppingcart.controller;

import com.atlavik.shoppingcart.constant.ProductConstants;
import com.atlavik.shoppingcart.constant.ShoppingCartConstants;
import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.ItemsDto;
import com.atlavik.shoppingcart.dto.ProductDto;
import com.atlavik.shoppingcart.dto.ShoppingCartDto;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.enums.Category;
import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.User;
import com.atlavik.shoppingcart.repository.UserRepository;
import com.atlavik.shoppingcart.service.ItemService;
import com.atlavik.shoppingcart.service.ProductService;
import com.atlavik.shoppingcart.service.ShoppingCartService;
import com.atlavik.shoppingcart.service.UserService;
import com.atlavik.shoppingcart.utils.JSON;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WithMockUser(
        username= UserConstants.USERNAME_TEST
        ,roles=UserConstants.USER_WRITE_PRIVILEGE )
class ShoppingCartControllerTest {
        @MockBean
        ShoppingCartService shoppingCartService;
        @Autowired
        MockMvc mockMvc;
        @Autowired
        UserService userService;
        @MockBean
        ProductService productService;
        @MockBean
        UserRepository userRepository;
        @MockBean
        ItemService itemService;
        @Autowired
        ModelMapper modelMapper;
        private ShoppingCartDto inputCart;
        String accessToken;
        @BeforeEach
        void setup() throws  Exception{
                ProductDto productDto=ProductDto.builder()
                        .category( Category.COMPUTER )
                        .description( ProductConstants.PRODUCT_DESC_TEST )
                        .price( 3.3 )
                        .name( ProductConstants.PRODUCT_NAME_TEST )
                        .build();
                ItemsDto itemsDto=ItemsDto.builder()
                        .shoppingCartDto( inputCart )
                        .product( productDto )
                        .build();

                UserDto  inputUserDto=UserDto.builder()
                        .id( 1L )
                        .phone( UserConstants.PHONE_TEST )
                        .password(  UserConstants.PASS_TEST )
                        .userName( UserConstants.USERNAME_TEST )
                        .build();

                inputCart= ShoppingCartDto.builder()
                        .country( Country.Brazil  )
                        .currency( Currency.USD )
                        .totalPrice( 3.3 )
                        .products( Collections.singletonList( itemsDto ) )
                        .user( inputUserDto )
                        .status( Status.OPENED )
                        .build();
               accessToken=obtainAccessToken( UserConstants.USERNAME_TEST,UserConstants.PASS_TEST );
        }
        @Test
        void itShouldGetCartOfUser() throws Exception{
                //Given
                BDDMockito.given(shoppingCartService.findCartByUserId( 1L) )
                        .willReturn( Optional.of( inputCart ) );
                //When
                ResultActions resultActions=this.mockMvc.perform( MockMvcRequestBuilders
                        .get( ShoppingCartConstants.CART_CONTEXT ).header("Authorization", "Bearer " + accessToken) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk());
        }
        @Test
        void itShouldNotGetCartOfUser() throws Exception{
                //Given
                BDDMockito.given(userService
                        .findUserByUserName( UserConstants.USERNAME_TEST )  )
                        .willReturn( Optional.empty()  );
                //When
                ResultActions resultActions=this.mockMvc.perform( MockMvcRequestBuilders
                        .get( ShoppingCartConstants.CART_CONTEXT ).header("Authorization", "Bearer " + accessToken) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk())
                        .andExpect( MockMvcResultMatchers.content()
                                .string( Matchers.containsString( ShoppingCartConstants.CART_MSG_NOT_FOUND ) ) );
        }
        @Test
        void itShouldAddShoppingCart()throws Exception{
                //Given
                BDDMockito.given(shoppingCartService
                        .findCartByStatusAndUser( Status.OPENED,UserConstants.USERNAME_TEST ))
                        .willReturn( Optional.empty() );
                String body= JSON.convertToJSON(inputCart);
                //When
                ResultActions resultActions=
                        this.mockMvc.perform( MockMvcRequestBuilders
                                .post( ShoppingCartConstants.CART_CONTEXT )
                                .header("Authorization", "Bearer " + accessToken)
                                .content(body)
                                .contentType( MediaType.APPLICATION_JSON ))
                                .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_CREATED_SUC ) ) );
                BDDMockito.then( productService ).should().checkListProduct( ArgumentMatchers.any() );
        }
        @Test
        void itShouldNotAddShoppingCart()throws Exception{
                //Given
                BDDMockito.given(shoppingCartService
                        .findCartByStatusAndUser( Status.OPENED,UserConstants.USERNAME_TEST ))
                        .willReturn( Optional.of(inputCart) );
                String body= JSON.convertToJSON(inputCart);
                //When
                ResultActions resultActions=
                        this.mockMvc.perform( MockMvcRequestBuilders
                                .post( ShoppingCartConstants.CART_CONTEXT )
                                .header("Authorization", "Bearer " + accessToken)
                                .content(body)
                                .contentType( MediaType.APPLICATION_JSON ))
                                .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_POST_OPENED ) ) );
        }
        @Test
        void itShouldRemoveProductFromCart() throws Exception{
                //Given
                BDDMockito.given( itemService
                        .findItemOrdersByParameters(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.of( inputCart.getProducts().get( 0 )) );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .delete(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_DEL,1L,1L )
                                .header("Authorization", "Bearer " + accessToken))

                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_DEL_SUC ) ) );
                BDDMockito
                        .then( itemService )
                        .should()
                        .deleteByShoppingIdAndProductId( ArgumentMatchers.any() );
                BDDMockito
                        .then( shoppingCartService )
                        .should()
                        .updateTotalPriceWithCartId( ArgumentMatchers.any() );
        }
        @Test
        void itShouldNotRemoveProductFromCart() throws Exception{
                //Given
                BDDMockito.given( itemService
                        .findItemOrdersByParameters(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.empty() );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .delete(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_DEL,1L,1L ) .header("Authorization", "Bearer " + accessToken))
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_OPR_NOT_FOUND ) ) );
        }
        @Test
        void itShouldGetProductFromCart() throws Exception{
                //Given
                BDDMockito.given( itemService
                        .findItemOrdersByParameters(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.of(inputCart.getProducts().get( 0 )) );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .get(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_GET,1L,1L ).header("Authorization", "Bearer " + accessToken) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() );
        }
        @Test
        void itShouldNotGetProductFromCart() throws Exception{
                //Given
                BDDMockito.given( itemService
                        .findItemOrdersByParameters(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.empty() );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .get(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_GET,1L,1L ).header("Authorization", "Bearer " + accessToken) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_MSG_NOT_FOUND ) ) );
        }
        @Test
        void itShouldUpdateProductFromCart() throws Exception{
                //Given
                BDDMockito.given( shoppingCartService
                        .findCartByIdAndUserName(ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.of(inputCart ) );
                BDDMockito.given( productService .findProductById( ArgumentMatchers.any() ))
                        .willReturn( Optional.of(  inputCart.getProducts().get( 0 ).getProduct() ) );

                String body=JSON.convertToJSON( inputCart.getProducts().get( 0 ).getProduct() );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .put(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_PUT,1L ).header("Authorization", "Bearer " + accessToken)
                                .content( body ).contentType( MediaType.APPLICATION_JSON ) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_PUT_SUC ) ) );
                BDDMockito.then( shoppingCartService ).should().updateTotalPriceWithCartId( ArgumentMatchers.any() );

        }
        @Test
        void itShouldNotUpdateProductFromCart() throws Exception{
                //Given
                BDDMockito.given( shoppingCartService
                        .findCartByIdAndUserName(ArgumentMatchers.any(),ArgumentMatchers.any() ) )
                        .willReturn( Optional.empty() );
                BDDMockito.given( productService .findProductById( ArgumentMatchers.any() ))
                        .willReturn( Optional.empty() );

                String body=JSON.convertToJSON( inputCart.getProducts().get( 0 ).getProduct() );
                //When
                ResultActions resultActions=this.mockMvc
                        .perform( MockMvcRequestBuilders
                                .put(ShoppingCartConstants.CART_CONTEXT+ ShoppingCartConstants.CART_PRODUCT_PUT,1L ).header("Authorization", "Bearer " + accessToken)
                                .content( body ).contentType( MediaType.APPLICATION_JSON ) )
                        .andDo( MockMvcResultHandlers.print() );
                //Then
                resultActions
                        .andExpect( MockMvcResultMatchers.status().isOk() )
                        .andExpect( MockMvcResultMatchers
                                .content().
                                        string( Matchers.containsString( ShoppingCartConstants.CART_OPR_NOT_FOUND ) ) );

        }

        private String obtainAccessToken(String username, String password) throws Exception {
                final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "password");
                params.add("username", username);
                params.add("password", password);
                inputCart.getUser().setPassword( (new BCryptPasswordEncoder()).encode(inputCart.getUser().getPassword()) );

                BDDMockito.given( userRepository.findUserByUserName( ArgumentMatchers.any() ) )
                        .willReturn(  Optional.of(modelMapper.map(inputCart.getUser(), User.class ) ) );

                ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                        .contentType( "application/x-www-form-urlencoded" )
                        .params(params)
                        .with(httpBasic("springbankClient", "springbankSecret"))
                )
                        .andDo( MockMvcResultHandlers.print() );

                String resultString = result.andReturn().getResponse().getContentAsString();

                JacksonJsonParser jsonParser = new JacksonJsonParser();
                return jsonParser.parseMap(resultString).get("access_token").toString();
        }

}