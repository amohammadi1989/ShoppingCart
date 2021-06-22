package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.ProductConstants;
import com.atlavik.shoppingcart.constant.UserConstants;
import com.atlavik.shoppingcart.dto.ProductDto;
import com.atlavik.shoppingcart.dto.UserDto;
import com.atlavik.shoppingcart.enums.Category;
import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.ItemOrders;
import com.atlavik.shoppingcart.model.Product;
import com.atlavik.shoppingcart.model.ShoppingCart;
import com.atlavik.shoppingcart.model.User;
import com.atlavik.shoppingcart.repository.ProductRepository;
import com.atlavik.shoppingcart.repository.ShoppingCartRepository;
import com.atlavik.shoppingcart.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created By: Ali Mohammadi
 * Date: 15 Jun, 2021
 */
@Service
public class InitService {
        private final UserRepository userRepository;
        private final ProductRepository productRepository;
        private  final ShoppingCartRepository shoppingCartRepository;
        private ModelMapper modelMapper;

        public InitService(
	      UserRepository userRepository,
	      ProductRepository productRepository,
	      ShoppingCartRepository shoppingCartRepository,
	      ModelMapper modelMapper
        ) {
	      this.userRepository = userRepository;
	      this.productRepository = productRepository;
	      this.shoppingCartRepository = shoppingCartRepository;
	      this.modelMapper = modelMapper;
        }

        @PostConstruct
        public  void init(){
	      //User init
	      User users = createNewUser();
	      //Product init
	      List<ItemOrders> orders = getItemOrders();
	      //Shopping Cart init
	      shoppingCartInit( users, orders );

        }

        private User createNewUser() {
	      BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(  );

	      UserDto userDto1=UserDto.builder()
		    .userName( UserConstants.USERNAME2_TEST )
		    .password( passwordEncoder.encode( UserConstants.PASS2_TEST ) )
		    .phone( UserConstants.PHONE2_TEST )
		    .build();
	      UserDto userDto=UserDto.builder()
		    .userName( UserConstants.USERNAME_TEST )
		    .password( passwordEncoder.encode( UserConstants.PASS_TEST ) )
		    .phone( UserConstants.PHONE_TEST )
		    .build();
	      userRepository.save( modelMapper.map( userDto1, User.class ));
	      return userRepository.save( modelMapper.map( userDto, User.class ));
        }

        @NotNull
        private List<ItemOrders> getItemOrders() {
	      Random random=new Random( );
	      List<ItemOrders> orders=new ArrayList<>(  );
	      for (int i=0;i<10;i++) {
		    ItemOrders itemOrders=new ItemOrders();
		    ProductDto pro = ProductDto.builder()
			  .category( Category.values()[random.nextInt( Category.values().length )] )
			  .description( ProductConstants.PRODUCT_DESC_TEST + i )
			  .name( ProductConstants.PRODUCT_NAME_TEST + i )
			  .id( (long) i )
			  .price( (double) random.nextInt( 100 ) )
			  .build();
		    itemOrders.setProduct(productRepository.save( modelMapper.map( pro, Product.class ) ));
		    orders.add( itemOrders );
	      }
	      return orders;
        }

        private void shoppingCartInit(User users, List<ItemOrders> orders) {
	      ShoppingCart shoppingCart=new ShoppingCart();
	      shoppingCart.setCountry( Country.Brazil );
	      shoppingCart.setCurrency( Currency.USD );
	      shoppingCart.setTotalPrice( 3.3 );
	      shoppingCart.setProducts(  orders );
	      shoppingCart.setStatus( Status.OPENED );
	      shoppingCart.setUser(users );
	      shoppingCartRepository.save( shoppingCart );
        }
}
