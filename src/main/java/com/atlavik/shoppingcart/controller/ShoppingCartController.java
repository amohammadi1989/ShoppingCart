package com.atlavik.shoppingcart.controller;

import com.atlavik.shoppingcart.constant.ShoppingCartConstants;
import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.*;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.service.ItemService;
import com.atlavik.shoppingcart.service.ProductService;
import com.atlavik.shoppingcart.service.ShoppingCartService;
import com.atlavik.shoppingcart.service.UserService;
import com.atlavik.shoppingcart.utils.SecurityContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@RestController
@RequestMapping(ShoppingCartConstants.CART_CONTEXT)
public class ShoppingCartController implements Serializable {
        private static final long serialVersionUID = 698430963573370396L;

        private ShoppingCartService shoppingCartService;
        private ProductService productService;
        private ItemService itemService;
        private UserService userService;


        public ShoppingCartController(ShoppingCartService shoppingCartService
                ,UserService userService
                ,ItemService itemService
                ,ProductService productService
        ) {
                this.shoppingCartService = shoppingCartService;
                this.userService=userService;
                this.itemService=itemService;
                this.productService=productService;
        }

        @GetMapping
        @PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        public ResponseEntity getCarts(){

                Optional<UserDto> userDto = getUserWithUserName();

                if(userDto.isPresent())
                        return  ResponseEntity.ok(
                                shoppingCartService.findCartByUserId( userDto.get().getId() )
                        );
                return  ResponseEntity.ok(
                        new ResponseDto(ShoppingCartConstants.CART_MSG_NOT_FOUND)
                );

        }

        @PostMapping
        @PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        public ResponseEntity addShoppingCart(@RequestBody ShoppingCartDto requestDto){
                String userName=SecurityContext.getUserName();
                Optional<ShoppingCartDto> findCart=shoppingCartService
                        .findCartByStatusAndUser( Status.OPENED,userName );
                if(!findCart.isPresent() &&
                        !requestDto.getProducts().isEmpty()) {
                        productService.checkListProduct( requestDto.getProducts());
                        requestDto.setTotalPrice(  itemService.sumTotalPrice( requestDto.getProducts() )  );
                        ShoppingCartDto newDto = shoppingCartService.addShoppingCart( requestDto );
                        return ResponseEntity.ok( new ResponseDto<>( ShoppingCartConstants.CART_CREATED_SUC, newDto ) );
                }else{
                        return ResponseEntity.ok(new ResponseDto<>( ShoppingCartConstants.CART_POST_OPENED ));
                }
        }

        private Optional<UserDto> getUserWithUserName() {
                return userService
                        .findUserByUserName( SecurityContext.getUserName() );
        }

        //@PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        //TODO :enable authrization
        @DeleteMapping(ShoppingCartConstants.CART_PRODUCT_DEL)
        @PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        public ResponseEntity removeProductFromCart(
                @PathVariable(value = ShoppingCartConstants.CART_ID)  Long cartid,
                @PathVariable(value = ShoppingCartConstants.CART_PRODUCT_ID) Long productId){
                String userName = SecurityContext.getUserName();
                Optional<ItemsDto> items=itemService.findItemOrdersByParameters( productId,   cartid , userName );

                if(items.isPresent()) {
                        itemService.deleteByShoppingIdAndProductId( items.get().getId() );
                        shoppingCartService.updateTotalPriceWithCartId( cartid );
                        return
                                ResponseEntity
                                        .ok( new ResponseDto<>( ShoppingCartConstants.CART_DEL_SUC ) );
                }
                else
                        return ResponseEntity.ok( new ResponseDto<>(ShoppingCartConstants.CART_OPR_NOT_FOUND ) );
        }

        @GetMapping(ShoppingCartConstants.CART_PRODUCT_GET)
        @PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        public ResponseEntity getProductFromCart(@PathVariable(value = ShoppingCartConstants.CART_ID)  Long cartid,
                                                 @PathVariable(value = ShoppingCartConstants.CART_PRODUCT_ID) Long productId){
                String userName=SecurityContext.getUserName();
                Optional<ItemsDto> item=itemService.findItemOrdersByParameters( productId,cartid,userName );
                if(item.isPresent()){
                        return  ResponseEntity.ok( item.get() );
                }
                return ResponseEntity.ok( new ResponseDto<>( ShoppingCartConstants.CART_MSG_NOT_FOUND ) );
        }

        @PutMapping(ShoppingCartConstants.CART_PRODUCT_PUT)
        @PreAuthorize("hasRole('"+ UserConstants.USER_WRITE_PRIVILEGE+"')")
        public ResponseEntity updateProductFromCart(@PathVariable(value = ShoppingCartConstants.CART_ID)  Long cartid,
                                                    @RequestBody ProductDto productDto){
                String userName=SecurityContext.getUserName();
                Optional<ShoppingCartDto> findDto=shoppingCartService.findCartByIdAndUserName( cartid,userName );
                if(findDto.isPresent() &&
                        productService.findProductById( productDto.getId() ).isPresent()
                ) {
                        ItemsDto itemsDto = ItemsDto
                                .builder()
                                .shoppingCartDto( ShoppingCartDto
                                        .builder().id( cartid ).build() ).product( productDto ).build();
                        ItemsDto newItem = itemService.addProductToCart( itemsDto );
                        shoppingCartService.updateTotalPriceWithCartId( cartid );
                        return ResponseEntity.ok( new ResponseDto<>( ShoppingCartConstants.CART_PUT_SUC ,newItem) );
                }else
                        return ResponseEntity.ok(new ResponseDto<>( ShoppingCartConstants.CART_OPR_NOT_FOUND ));
        }
}
