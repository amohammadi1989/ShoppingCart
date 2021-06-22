package com.atlavik.shoppingcart.constant;

import javax.persistence.criteria.CriteriaBuilder;
import javax.print.DocFlavor;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public interface UserConstants {
        String PASSWORD_MIN_MSG="Length must be more than 6";
        int PASSWORD_MIN=6;
        String USERNAME_MIN_MSG="Length must be more than 5";
        int USERNAME_MIN=5;
        String PHONE_MIN_MSG="Length must be more than 11";
        int PHONE_MIN=11;
        String USER_FIND_MSG="No user was found with this ID";
        String USER_INVALID="Incorrect username/password supplied";
        String USER_CONTEXT="/shopping/user";
        String USER_SINGIN="/login";
        String USERNAME_TEST="TEST1";
        String PASS_TEST="123456";
        String PHONE_TEST="98123657854";

        String USERNAME2_TEST="TEST2";
        String PASS2_TEST="123456";
        String PHONE2_TEST="98123657851";

        String USERNAME_CONFLICT="The username has already been registered ";
        String USER_CREATEED="New User  successfully created";
        String USER_READ_PRIVILEGE="READ_PRIVILEGE";
        String USER_WRITE_PRIVILEGE="WRITE_PRIVILEGE";
}
