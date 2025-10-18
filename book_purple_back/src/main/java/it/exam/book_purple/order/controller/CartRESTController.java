package it.exam.book_purple.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.order.dto.CartDTO;
import it.exam.book_purple.order.service.CartService;
import it.exam.book_purple.security.dto.UserSecureDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CartRESTController {

    public final CartService cartService;

    // 장바구니 조회
    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getCart(@AuthenticationPrincipal UserSecureDTO user) throws Exception{
        Map<String,Object> resultMap = cartService.getCart(user);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 장바구니 추가
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<Map<String,Object>>> addCart(@RequestBody CartDTO.CartRequest request) throws Exception{
        Map<String,Object> resultMap = cartService.addCart(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 장바구니 삭제
    @DeleteMapping("/cart")
    public ResponseEntity<ApiResponse<Map<String,Object>>> deleteCart(@RequestBody List<Integer> cItems) throws Exception{
        Map<String,Object> resultMap = cartService.deleteCart(cItems);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

}
