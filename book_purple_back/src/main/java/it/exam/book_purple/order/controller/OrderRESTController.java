package it.exam.book_purple.order.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.order.dto.OrderDTO;
import it.exam.book_purple.order.service.OrderService;
import it.exam.book_purple.security.dto.UserSecureDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderRESTController {

    private final OrderService orderService;

    // 주문내역 조회
    @GetMapping("/order")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getOrder(@AuthenticationPrincipal UserSecureDTO user) throws Exception{
        Map<String,Object> resultMap = orderService.getOrder(user);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 장바구니에서 구매
    @PostMapping("/order/cart")
    public ResponseEntity<ApiResponse<Map<String,Object>>> orderCart(@RequestBody OrderDTO.OrderRequest request) throws Exception{
        Map<String,Object> resultMap = orderService.orderCart(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 상세페이지에서 구매
    @PostMapping("/order/direct")
    public ResponseEntity<ApiResponse<Map<String,Object>>> orderDirect(@RequestBody OrderDTO.OrderRequest request) throws Exception{
        Map<String,Object> resultMap = orderService.orderDirect(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }
}
