package com.atlavik.shoppingcart.repository;

import com.atlavik.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findUserByUserName(String userName);
}
