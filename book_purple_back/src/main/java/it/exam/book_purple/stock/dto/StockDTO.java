package it.exam.book_purple.stock.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.exam.book_purple.book.enums.BookStatus;
import it.exam.book_purple.stock.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StockDTO {

   // 재고 리스트
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class StockList{

        private int stockId;
        private int bookId;
        private String title;
        private int quantity;
        private BookStatus status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateDate;

         public static StockList of (StockEntity entity){

            return StockList.builder()
                            .stockId(entity.getStockId())
                            .bookId(entity.getBook().getBookId())
                            .title(entity.getBook().getTitle())
                            .quantity(entity.getQuantity())
                            .status(entity.getBook().getStatus())
                            .createDate(entity.getCreateDate())
                            .updateDate(entity.getUpdateDate())
                            .build();
        }

        public LocalDateTime getModifiedDate(){
            return this.updateDate == null ? this.createDate : this.updateDate;
        }

    }

    // 재고 상세
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class StockDetail{

        private int stockId;
        private String title;
        private int quantity;
        private BookStatus status;
        private String delYn;

        public static StockDetail of (StockEntity entity){

            return StockDetail.builder()
                              .stockId(entity.getStockId())
                              .title(entity.getBook().getTitle())
                              .quantity(entity.getQuantity())
                              .status(entity.getBook().getStatus())
                              .delYn(entity.getBook().getDelYn())
                              .build();
        }

    }

    // 재고 수정
    @Data
    public static class StockRequest{

        private int stockId;
        private int quantity;
        private BookStatus status;
         
    }

}
