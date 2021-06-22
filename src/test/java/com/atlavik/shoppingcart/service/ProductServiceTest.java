package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.constant.ProductConstants;
import com.atlavik.shoppingcart.dto.ItemsDto;
import com.atlavik.shoppingcart.dto.ProductDto;
import com.atlavik.shoppingcart.enums.Category;
import com.atlavik.shoppingcart.exception.ProductException;
import com.atlavik.shoppingcart.model.Product;
import com.atlavik.shoppingcart.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@SpringBootTest
class ProductServiceTest {
        @Autowired
        ProductService productServiceTest;
        @MockBean
        ProductRepository productRepository;
        @Autowired
        ModelMapper modelMapper;
        private ProductDto inputDto;
        @BeforeEach
        void setUp() {
                inputDto=ProductDto.builder()
                        .category( Category.COMPUTER )
                        .name( ProductConstants.PRODUCT_NAME_TEST )
                        .price( ProductConstants.PRODUCT_PRICE_TEST )
                        .description( ProductConstants.PRODUCT_DESC_TEST )
                        .id( 1L )
                        .build();
        }
        @Test
        void itShouldFindProductById(){
                //Given
                Product product=modelMapper.map( inputDto,Product.class );
                BDDMockito.given( productRepository.findById(1L ) )
                        .willReturn( Optional.of(product));
                //When
                Optional<ProductDto> returnDto=productServiceTest
                        .findProductById( 1L );
                //Then
                Assertions.assertThat( returnDto).isEqualTo( Optional.of(inputDto) );
        }
        @Test
        void itShouldEmptyWhenFindProductById(){
                //Given
                BDDMockito.given( productRepository.findById(1L ) )
                        .willReturn( Optional.empty());
                //When
                Optional<ProductDto> returnDto=productServiceTest
                        .findProductById( 1L );
                //Then
                Assertions.assertThat( returnDto).isEqualTo( Optional.empty());
        }
        @Test
        void itShouldCheckListProductWithoutException(){
                //Given
                Product product=modelMapper.map( inputDto,Product.class );
                ItemsDto itemsDto= ItemsDto
                        .builder()
                        .id( 1L )
                        .product( inputDto )
                        .build();
                List<ItemsDto> itemOrders= Collections.singletonList( itemsDto );
                BDDMockito.given( productRepository.findById(1L ) )
                        .willReturn( Optional.of(product));
                //When
                productServiceTest
                        .checkListProduct(itemOrders );
                //Then

        }
        @Test
        void itShouldThrowExceptionWhenCheckListProduct(){
                //Given
                ItemsDto itemsDto= ItemsDto
                        .builder()
                        .id( 2L )
                        .product( inputDto )
                        .build();
                List<ItemsDto> itemOrders= Collections.singletonList( itemsDto );
                BDDMockito.given( productRepository.findById(1L ) )
                        .willReturn( Optional.empty());
                //When
                //Then
                Assertions.assertThatThrownBy( ()->     productServiceTest
                        .checkListProduct(itemOrders ) )
                        .isInstanceOf( ProductException.class )
                        .hasMessageContaining( ProductConstants.PRODUCT_ITEM_INVALID );

        }
}