package it.exam.book_purple.order.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.exam.book_purple.order.dto.CartItemDTO.CartItem;
import it.exam.book_purple.order.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CartResponse{
        private int cartId;
        private String userId;
        private List<CartItem> cartItems;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private int point;
        
        public static CartResponse of (CartEntity entity){
            
            List<CartItem> cartItems = entity.getCItems().stream()
                                      .map(CartItemDTO.CartItem::of)
                                      .toList();
            return CartResponse.builder()
                               .cartId(entity.getCartId())
                               .userId(entity.getUser().getUserId())
                               .cartItems(cartItems)
                               .point(entity.getUser().getPoint())
                               .build();

        }
    }

    @Data
    public static class CartRequest {
        
        private String userId;
        private int bookId;
        private int quantity;
        
    } 
}
