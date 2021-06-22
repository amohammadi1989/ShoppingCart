package com.atlavik.shoppingcart.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data

public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3731876455270075297L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Id
    private Long id;
    @Column(nullable = false,name = "USERNAME",unique = true)
    private String userName;
    @Column(nullable = false,name ="PASSWORD")
    private String password;
    @Column(nullable = false,name = "PHONE",length = 11)
    private String phone;
    @OneToMany(
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name="USER_ID")
    private List<ShoppingCart> shoppingCarts;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" +super.getCreateTime() +
                ", updateTime="+ super.getUpdateTime() +
                '}';
    }

}
