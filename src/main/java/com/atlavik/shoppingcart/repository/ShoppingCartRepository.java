package com.atlavik.shoppingcart.repository;

import com.atlavik.shoppingcart.constant.ShoppingCartConstants;
import com.atlavik.shoppingcart.enums.Status;
import com.atlavik.shoppingcart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart,Long> {
        Optional<ShoppingCart> findShoppingCartByUserId(Long id);
     //   Optional<ShoppingCart> findByIdAndUserId(Long id,Long userId);
        @Query(value = ShoppingCartConstants.CART_QRY_STATUS,nativeQuery = true)
        Optional<ShoppingCart> findShoppingCartByStatusAndUser(String status,String userName);
        @Query(value = ShoppingCartConstants.CART_QRY_ID_USER,nativeQuery = true)
        Optional<ShoppingCart> findShoppingCartByIdAndUser(Long cartId,String userName);
        @Modifying
        @Transactional
        @Query(value = ShoppingCartConstants.CART_UPDATE_PRICE,nativeQuery = true)
        int updatePriceWithCartId(Long id);
}
