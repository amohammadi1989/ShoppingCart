package com.atlavik.shoppingcart.service;

import com.atlavik.shoppingcart.dto.ItemsDto;
import com.atlavik.shoppingcart.model.ItemOrders;
import com.atlavik.shoppingcart.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 16 Jun, 2021
 */
@Service
public class ItemService {
        private final ItemRepository itemRepository;
        private ModelMapper modelMapper;

        public ItemService(ItemRepository itemRepository, ModelMapper modelMapper) {
	      this.itemRepository = itemRepository;
	      this.modelMapper = modelMapper;
        }

        public Optional<ItemsDto> findItemOrdersByParameters(Long productId,Long shopId,String userName){
                Optional<ItemOrders> itemOrders=
		    itemRepository.findItemOrdersByParameters( productId,shopId,userName );
                return itemOrders.map( i->modelMapper.map( i,ItemsDto.class ) );
        }
        public void deleteByShoppingIdAndProductId(Long orderId){
                itemRepository.deleteById( orderId );
        }

        public ItemsDto addProductToCart(ItemsDto itemsDto) {
	      ItemOrders itemOrders=modelMapper.map( itemsDto,ItemOrders.class );

	      return modelMapper.map(itemRepository.save( itemOrders ),ItemsDto.class );
        }
        public Double sumTotalPrice(List<ItemsDto> itemsDtos){
                return itemsDtos
		    .stream()
		    .filter( i->i.getProduct()!=null && i.getProduct().getPrice()!=null )
		    .mapToDouble( i->i.getProduct().getPrice() ).sum();
        }
}
