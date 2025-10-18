package it.exam.book_purple.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.common.utils.CookieUtils;
import it.exam.book_purple.common.utils.JWTUtils;
import it.exam.book_purple.security.filter.LoginFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RefreshTokenController {

    private final JWTUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @GetMapping("/refresh")
    // ? : 어떤 거 쓸지 모름 >> 다 쓸 수 있음
    public ResponseEntity<?> refreshToken(HttpServletRequest request,
                                            HttpServletResponse response) throws Exception{

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(refreshToken == null || !jwtUtils.validateToken(refreshToken)){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid refresh token");
        }

        String userId = jwtUtils.getUserId(refreshToken);
        String userName = jwtUtils.getUserName(refreshToken);
        String userRole = jwtUtils.getUserRole(refreshToken);

        // 토큰 생성
        String accessToken = jwtUtils.createJWT("access", userId, userName, 
                            userRole, LoginFilter.ACCESS_TOKEN_EXPIRE_TIME);
        String newRefresh = jwtUtils.createJWT("refresh", userId, userName, 
                            userRole, LoginFilter.REFRESH_TOKEN_EXPIRE_TIME);

        // 응답을 설정
        // 쿠키에 갱신된 refresh 토큰 넣기
        Cookie cookie = cookieUtils.createCookie("refresh", newRefresh, 
                        (int)LoginFilter.ACCESS_TOKEN_EXPIRE_TIME);
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

        // 인증정보
        Map<String,Object> mapContent = new HashMap<>();
        mapContent.put("resultMsg", "OK");
        mapContent.put("status", "200");

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("userName", userName);
        data.put("userRole", userRole);
        data.put("token", accessToken);

        mapContent.put("content", data);

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(ApiResponse.ok(mapContent));

    }

}
