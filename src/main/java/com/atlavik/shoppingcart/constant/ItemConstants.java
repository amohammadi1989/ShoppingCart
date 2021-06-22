package com.atlavik.shoppingcart.constant;

/**
 * Created By: Ali Mohammadi
 * Date: 16 Jun, 2021
 */
public interface ItemConstants {
        String ITEM_DEL_QRY="delete  from ITEM_ORDERS  i WHERE  i.PRODUCT_ID=?1 and i.SHOPPING_CART_ID IN (SELECT s.ID FROM SHOPPING_CART s where s.ID=?2 AND s.USER_ID IN(SELECT u.id FROM USERS u where u.USERNAME=?3)) ";
        String ITEM_SEL_QRY="select top 1 o.* from Item_orders o join shopping_cart  s on o.shopping_cart_id =s.id\n" +
	      " where o.product_id=?1 and o.shopping_cart_id =?2  and s.User_id =(select u.id from USERS u where u.username=?3)";
}
