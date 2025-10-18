package it.exam.book_purple.order.dto;

import it.exam.book_purple.order.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderItemDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class OrderItem {
        private int oitemId;
        private int orderId;
        private int bookId;
        private String imageUrl;
        private String title;
        private int price;
        private int quantity;
        private int totalPrice;

        public static OrderItem of(OrderItemEntity entity){
            return OrderItem.builder()
                    .oitemId(entity.getOitemId())
                    .orderId(entity.getOrder().getOrderId())
                    .bookId(entity.getBook().getBookId())
                    .imageUrl(entity.getBook().getImageUrl())
                    .title(entity.getBook().getTitle())
                    .price(entity.getBook().getPrice())
                    .quantity(entity.getQuantity())
                    .totalPrice(entity.getBook().getPrice() * entity.getQuantity())
                    .build();
        }
    }

    @Data
    public static class OrderItemRequest {
        private int bookId;
        private int quantity;
        private int price;
        private int citemId;
    }

}
