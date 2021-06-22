package com.atlavik.shoppingcart.repository;

import com.atlavik.shoppingcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface ProductRepository extends JpaRepository<Product,Long> {

}
