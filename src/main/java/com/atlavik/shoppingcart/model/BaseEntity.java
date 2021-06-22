package com.atlavik.shoppingcart.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created By: Ali Mohammadi
 * Date: 14 Jun, 2021
 */
@Data
@MappedSuperclass
public class BaseEntity  implements Serializable {
       // @CreationTimestamp
      //  @Temporal( TemporalType.TIMESTAMP )
        @Column(name="CREATE_TIME",updatable = false)
        private LocalDateTime createTime;
       // @CreationTimestamp
       //@Temporal( TemporalType.TIMESTAMP )
        @Column(name="UPDATE_TIME")
        private LocalDateTime updateTime;
        @PrePersist
        public void prePersist(){
                this.updateTime= LocalDateTime.now();
                this.createTime=LocalDateTime.now();
        }
        @PreUpdate
        public void perUpdate(){
                this.updateTime=LocalDateTime.now();
        }
}
