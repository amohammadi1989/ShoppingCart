package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.dto.ShoppingCartDto;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.ShoppingCart;
import com.atlavik.shoppingcart.repository.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@Service
public class ShoppingCartService {
        private ModelMapper modelMapper;
        private ShoppingCartRepository shoppingCartRepository;

        public ShoppingCartService(ModelMapper modelMapper, ShoppingCartRepository shoppingCartRepository) {
	      this.modelMapper = modelMapper;
	      this.shoppingCartRepository = shoppingCartRepository;
        }
        public Optional<ShoppingCartDto> findCartByUserId(Long id){
	      Optional<ShoppingCart>  shoppingCart=shoppingCartRepository.findShoppingCartByUserId( id );
	      return shoppingCart.map( cart -> modelMapper.map( cart, ShoppingCartDto.class ) );
        }
        public Optional<ShoppingCartDto> findCartByIdAndUserName(Long cartId,String userName){
	      Optional<ShoppingCart>  shoppingCart=shoppingCartRepository.findShoppingCartByIdAndUser( cartId,userName );
	      return  shoppingCart.map(s->modelMapper.map(s,ShoppingCartDto.class ));
        }
        public Optional<ShoppingCartDto> findCartByStatusAndUser(Status status, String userName){
	      Optional<ShoppingCart>  shoppingCart=shoppingCartRepository
		    .findShoppingCartByStatusAndUser( status.name(),userName );
	      return  shoppingCart.map(s->modelMapper.map(s,ShoppingCartDto.class ));
        }

        public int updateTotalPriceWithCartId(Long cartId){
	      return shoppingCartRepository.updatePriceWithCartId( cartId );
        }

        public ShoppingCartDto addShoppingCart(ShoppingCartDto shoppingCartDto) {
	      ShoppingCart shoppingCart= shoppingCartRepository
		    .save( modelMapper
			  .map( shoppingCartDto,ShoppingCart.class ) );
	      return modelMapper.map( shoppingCart,ShoppingCartDto.class );
        }


}

