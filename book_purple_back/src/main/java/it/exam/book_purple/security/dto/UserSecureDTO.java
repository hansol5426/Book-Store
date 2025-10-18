package it.exam.book_purple.security.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserSecureDTO extends User{

    private static final String ROLE_PREFIX = "ROLE_";

    private String userId;
    private String userName;
    
    public UserSecureDTO(String userId, String userName, String passwd, String userRole) {
        super(userId, passwd, makeGrantedAuthorities(userRole));

        this.userId = userId;
        this.userName = userName;
    }

    private static  List<GrantedAuthority> makeGrantedAuthorities(String userRole) {
        List<GrantedAuthority> list = new ArrayList<>();

        if(userRole.startsWith(ROLE_PREFIX)) {
            list.add(new SimpleGrantedAuthority(userRole));
        } else {
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole));
        }
        
        return list;
    }
}
