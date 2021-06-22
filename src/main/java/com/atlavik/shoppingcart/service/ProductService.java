package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.ProductConstants;
import com.atlavik.shoppingcart.dto.ItemsDto;
import com.atlavik.shoppingcart.dto.ProductDto;
import com.atlavik.shoppingcart.exception.ProductException;
import com.atlavik.shoppingcart.model.Product;
import com.atlavik.shoppingcart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@Service
public class ProductService {
        private ProductRepository productRepository;
        private ModelMapper modelMapper;

        public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
	      this.productRepository = productRepository;
	      this.modelMapper = modelMapper;
        }
        public Optional<ProductDto> findProductById(Long productId){
                Optional<Product> product=productRepository.findById( productId );
                return product.map( p->modelMapper.map(p,ProductDto.class ) );
        }
        public void checkListProduct(List<ItemsDto> dtoList) throws RuntimeException{
                dtoList.parallelStream().forEach( p->{
                       if(!productRepository.findById( p.getProduct().getId() ).isPresent())
                               throw new ProductException( ProductConstants.PRODUCT_ITEM_INVALID );
	      } );
        }


}
