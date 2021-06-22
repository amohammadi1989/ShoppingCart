package com.atlavik.shoppingcart.model;

import com.atlavik.shoppingcart.enums.Country;
import com.atlavik.shoppingcart.enums.Currency;
import com.atlavik.shoppingcart.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="SHOPPING_CART")
@Data
public class ShoppingCart extends BaseEntity implements Serializable {
        private static final long serialVersionUID = 6885090949118766061L;
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @ManyToOne
        private User user;
        @OneToMany(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY,orphanRemoval = true)
        @JoinColumn(name="SHOPPING_CART_ID")
        private List<ItemOrders> products;
        @Column(name = "TOTAL_PRICE")
        private Double totalPrice=0.0;
        @Column(name="CURRENCY")
        @Enumerated(EnumType.STRING)
        private Currency currency;
        @Column(name="COUNTRY_CODE")
        @Enumerated(EnumType.STRING)
        private Country country;
        @Column(name="STATUS")
        @Enumerated(EnumType.STRING)
        private Status status;

}
