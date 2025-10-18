package it.exam.book_purple.order.dto;

import it.exam.book_purple.order.entity.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartItemDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CartItem{
        
        private int citemId;
        private int cartId;
        private int bookId;
        private String imageUrl;
        private String title;
        private int price;
        private int quantity;
        private int totalPrice;

        public static CartItem of(CartItemEntity entity){
            return CartItem.builder()
                           .citemId(entity.getCitemId())
                           .cartId(entity.getCart().getCartId())
                           .bookId(entity.getBook().getBookId())
                           .imageUrl(entity.getBook().getImageUrl())
                           .title(entity.getBook().getTitle())
                           .price(entity.getBook().getPrice())
                           .quantity(entity.getQuantity())
                           .totalPrice(entity.getBook().getPrice() * entity.getQuantity())
                           .build();
        }    
    }
}
