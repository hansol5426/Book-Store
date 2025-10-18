package it.exam.book_purple.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.user.dto.UserDTO;
import it.exam.book_purple.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserRESTController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String,Object>>> addUser(@RequestBody UserDTO.RegisterRequest request) throws Exception{
        Map<String,Object> resultMap = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 회원 탈퇴
    @DeleteMapping("/withdraw/{userId}")
    public ResponseEntity<ApiResponse<Map<String,Object>>> withdraw(@PathVariable("userId") String userId) throws Exception{
        
        Map<String,Object> resultMap = userService.withdraw(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

}
