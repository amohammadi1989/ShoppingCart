package com.atlavik.shoppingcart.dto;

import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created By: Ali Mohammadi
 * Date: 15 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -963233916675203411L;
        @JsonIgnore
        private Long id;
        @JsonIgnore
        private UserDto user;
        private List<ItemsDto> products;
        private Double totalPrice=0.0;
        private Currency currency=Currency.USD;
        private Country country=Country.US;
        private Status status=Status.OPENED;
}
