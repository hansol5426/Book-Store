package it.exam.book_purple.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity {
    
    @Id
    private String userId;

    private String password;
    private String userName;
    private String email;
    private String address;
    private String addressDetail;
    private String phone;
    private LocalDateTime createDate;
    @Column( columnDefinition = "CHAR(1)")
    @ColumnDefault("N")
    private String delYn;
    private int point;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="role_id")
    private UserRoleEntity role;

}
