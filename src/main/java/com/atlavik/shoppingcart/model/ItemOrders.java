package com.atlavik.shoppingcart.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 14 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ITEM_ORDERS")
@Data
public class ItemOrders  extends BaseEntity implements Serializable {
        private static final long serialVersionUID = 303461157942922442L;
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @OneToOne
        private Product product;
        @ManyToOne
        private ShoppingCart shoppingCart;
}
