package com.atlavik.shoppingcart.constant;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface ShoppingCartConstants {
        String CART_CONTEXT="/api/carts";
        String CART_PRODUCT_DEL="/{cartId}/products/{productId}";
        String CART_PRODUCT_GET="/{cartId}/products/{productId}";
        String CART_PRODUCT_PUT="/{cartId}/products";
        String CART_ID="cartId";
        String CART_PRODUCT_ID="productId";
        String CART_CREATED_SUC="Cart was created successfully";
        String CART_MSG_NOT_FOUND="Sorry, no output was found";
        String CART_DEL_SUC="The product was successfully removed from the cart";
        String CART_PUT_SUC="The product was successfully added to the cart";
        String CART_OPR_NOT_FOUND ="No record found for operation";
        String CART_POST_OPENED="You now have an open shopping cart. You cannot create a new shopping cart";
        String CART_QRY_ID_USER="SELECT s.* FROM SHOPPING_CART s join USERS u on s.USER_ID =u.id where s.id=?1 and u.username=?2" ;
        String CART_UPDATE_PRICE="update shopping_cart  s set s.total_price=(select sum(p.price) from item_orders o join products p on o.product_id=p.id\n" +
                " where o.shopping_cart_id=?1) where id=?1";
        String CART_QRY_STATUS="SELECT s.* FROM SHOPPING_CART s join USERS u on s.user_id=u.id where s.status=?1 AND u.username=?2";
}
