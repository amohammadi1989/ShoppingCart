package com.atlavik.shoppingcart.repository;

import com.atlavik.shoppingcart.constant.ItemConstants;
import com.atlavik.shoppingcart.model.ItemOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface ItemRepository extends JpaRepository<ItemOrders,Long> {

/*        @Transactional
        @Modifying
        @Query(value = ItemConstants.ITEM_DEL_QRY,nativeQuery = true)
        void deleteByProductIdAndShoppingCartId
                (
                 Long productId,
                 Long shoppingId,
                 String userName
                );*/
        @Query(nativeQuery = true,value =ItemConstants.ITEM_SEL_QRY)
        Optional<ItemOrders> findItemOrdersByParameters
                (
                        Long productId,
                        Long shoppingId,
                        String userName
                );
}
