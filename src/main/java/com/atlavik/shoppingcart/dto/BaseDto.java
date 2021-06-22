package com.atlavik.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 14 Jun, 2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
 class BaseDto implements Serializable {
        private String createTime;
        private String updateTime;
}
