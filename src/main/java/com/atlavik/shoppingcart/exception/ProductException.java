package com.atlavik.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductException extends RuntimeException{

    private static final long serialVersionUID = 8648557282202896720L;

    public ProductException(String message) {
        super( message );
    }
}
