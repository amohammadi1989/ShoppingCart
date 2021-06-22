package com.atlavik.shoppingcart.dto;

import com.atlavik.shoppingcart.model.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 15 Jun, 2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto  implements Serializable {
        private static final long serialVersionUID = 8533684956352628023L;
        @JsonIgnore
        private Long id;
        private ProductDto product;
        @JsonIgnore
        private ShoppingCartDto shoppingCartDto;
}
