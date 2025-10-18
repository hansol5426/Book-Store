package it.exam.book_purple.user.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.exam.book_purple.user.dto.UserDTO;
import it.exam.book_purple.user.entity.UserEntity;
import it.exam.book_purple.user.entity.UserRoleEntity;
import it.exam.book_purple.user.repository.UserRepository;
import it.exam.book_purple.user.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;    

    // 회원 가입
    public Map<String,Object> addUser(UserDTO.RegisterRequest request) throws Exception{

        Map<String, Object> resultMap = new HashMap<>();

        if(userRepository.findByIdAndNotDeleted(request.getUserId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자 입니다.");
        }

        UserEntity userEntity = UserDTO.to(request);

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setDelYn("N");
        userEntity.setPoint(100);

        UserRoleEntity roleEntity = userRoleRepository.findById("USER").orElseThrow(() -> new RuntimeException("USER 권한이 존재하지 않습니다."));

        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");   

        return resultMap;

    }

    // 회원 삭제
    @Transactional
    public Map<String,Object> withdraw(String userId) throws Exception{

        Map<String ,Object> resultMap = new HashMap<>();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new RuntimeException("찾는 유저가 없습니다."));
        
        userEntity.setDelYn("Y");
        
        userRepository.save(userEntity);
        
        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;   

    }

}
