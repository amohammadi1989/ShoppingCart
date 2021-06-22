package com.atlavik.shoppingcart.dto;

import com.atlavik.shoppingcart.constant.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created By: Ali Mohammadi
 * Date: 13 Jun, 2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
        private static final long serialVersionUID = -5025549669958312344L;
        private Long id;
       @NotEmpty
        @Size(min = UserConstants.USERNAME_MIN,message = UserConstants.USERNAME_MIN_MSG)
        private String userName;
        @NotEmpty
        @Size(min = UserConstants.PASSWORD_MIN, message = UserConstants.PASSWORD_MIN_MSG)
        private String password;
        @NotEmpty
        @Size(min=UserConstants.PHONE_MIN,message = UserConstants.PHONE_MIN_MSG)
        private String phone;

        private String updateTime;

        private String createTime;

        @Override
        public String toString() {
	      return "UserDto{" +
		    "userName='" + userName + '\'' +
		    ", password='" + password + '\'' +
		    ", phone='" + phone + '\'' +
		    ", createDate=" + createTime +
		    ", updateDate=" + updateTime +
		    '}';
        }
}
