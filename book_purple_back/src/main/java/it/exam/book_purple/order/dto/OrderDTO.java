package it.exam.book_purple.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.exam.book_purple.order.dto.OrderItemDTO.OrderItem;
import it.exam.book_purple.order.dto.OrderItemDTO.OrderItemRequest;
import it.exam.book_purple.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class OrderResponse {
        private int orderId;
        private String userId;
        @JsonFormat(pattern = "yyyy년 MM월 dd일")
        private LocalDateTime orderDate;
        private int totalPrice;
        private String status;
        private List<OrderItem> orderItems;

        public static OrderResponse of(OrderEntity entity){
            List<OrderItem> orderItems = entity.getOrderItems().stream()
                                          .map(OrderItemDTO.OrderItem::of)
                                          .toList();

            return OrderResponse.builder()
                                .orderId(entity.getOrderId())
                                .userId(entity.getUser().getUserId())
                                .orderDate(entity.getOrderDate())
                                .totalPrice(entity.getTotalPrice())
                                .status(entity.getStatus())
                                .orderItems(orderItems)
                                .build();
        }
    }

    @Data
    public static class OrderRequest {
        private String userId;
        private List<OrderItemRequest> orderItems;
        private int totalPrice;
    }

}
