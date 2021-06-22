package com.atlavik.shoppingcart.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created By: Ali Mohammadi
 * Date: 13 Jun, 2021
 */
@Data
public final class ResponseDto<T>  implements Serializable {
        private static final long serialVersionUID = -7223531739271195121L;
        String message;
        T responseObj;
         Date  date;
        public ResponseDto(String message, T responseObj) {
	      this.message = message;
	      this.responseObj = responseObj;
	      this.date=new Date(  );
        }

        public ResponseDto(String message) {
	      this.message = message;
	      this.date=new Date(  );
        }
}
