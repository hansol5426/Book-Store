package it.exam.book_purple.user.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.exam.book_purple.user.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDTO {

    // 회원리스트
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserList{

        private String userName;
        private String userId;
        private String email;
        private String phone;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime createDate;
        private String delYn;
        private String roleId;

        public static UserList of (UserEntity entity){

            return UserList.builder()
                           .userName(entity.getUserName())
                           .userId(entity.getUserId())
                           .email(entity.getEmail())
                           .phone(entity.getPhone())
                           .createDate(entity.getCreateDate())
                           .delYn(entity.getDelYn())
                           .roleId(entity.getRole().getRoleId())
                           .build();
        }
    }

    // 회원가입정보
    @Data
    public static class RegisterRequest{

        @NotBlank(message="아이디는 필수 항목입니다.")
        private String userId;
        @NotBlank(message="비밀번호는 필수 항목입니다.")
        private String password;
        @NotBlank(message="이름은 필수 항목입니다.")
        private String userName;
        private String phone;
        private String email;
        private String address;
        private String addressDetail;
        private String roleId;
        private String roleName;

    }

    public static UserEntity to(UserDTO.RegisterRequest request){
        UserEntity entity = new UserEntity();
        entity.setUserId(request.getUserId());
        entity.setPassword(request.getPassword());
        entity.setUserName(request.getUserName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setAddress(request.getAddress());
        entity.setAddressDetail(request.getAddressDetail());
        return entity;
    }

}
