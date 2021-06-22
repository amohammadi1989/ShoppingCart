package com.atlavik.shoppingcart.dto;

import com.atlavik.shoppingcart.enums.Category;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName(value = "")
public class ProductDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -4294483187724024035L;
        private Long id;

        private String name;

        private String description;

        private Category category;

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
