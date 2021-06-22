package com.atlavik.shoppingcart.model;

import com.atlavik.shoppingcart.enums.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="products")
@Data
public class Product  extends BaseEntity implements Serializable {
        private static final long serialVersionUID = -4294483187724024035L;
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        @Column(name = "ID")
        private Long id;
        @Column(name = "NAME",nullable = false)
        private String name;
        @Column(name="DESCRIPTION")
        private String description;
        @Column(name = "CATEGORY",nullable = false)
        @Enumerated(EnumType.STRING)
        private Category category;
        @Column(name = "PRICE",nullable = false)
        private Double price;

        @Override
        public String toString() {
	      return "Product{" +
		    "id=" + id +
		    ", name='" + name + '\'' +
		    ", description='" + description + '\'' +
		    ", category=" + category +
		    ", createTime=" + super.getCreateTime() +
		    ", updateTime="+super.getUpdateTime() +
		    '}';
        }
}
