package it.exam.book_purple.book.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.book.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AdminBookDTO {

    // 도서 리스트
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AdminBookList{

        private int bookId;
        private String title;
        private String author;
        private String publisher;
        private LocalDate publicationDate;
        private int price;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime updateDate;
        private String delYn;

        public static AdminBookList of (BookEntity entity){

            return AdminBookList.builder()
                                .bookId(entity.getBookId())
                                .title(entity.getTitle())
                                .author(entity.getAuthor())
                                .publisher(entity.getPublisher())
                                .publicationDate(entity.getPublicationDate())
                                .price(entity.getPrice())
                                .createDate(entity.getCreateDate())
                                .updateDate(entity.getUpdateDate())
                                .delYn(entity.getDelYn())
                                .build();
        }

        public LocalDateTime getModifiedDate(){
            return this.updateDate == null ? this.createDate : this.updateDate;
        }
    }

    // 상세도서
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BookDetail{

        private int bookId;
        private String title;
        private String author;
        private String publisher;
        private LocalDate publicationDate;
        private int price;
        private String description;
        private String imageUrl;
        private String delYn;

        public static BookDetail of (BookEntity entity){

            return BookDetail.builder()
                             .bookId(entity.getBookId())
                             .title(entity.getTitle())
                             .author(entity.getAuthor())
                             .publisher(entity.getPublisher())
                             .publicationDate(entity.getPublicationDate())
                             .price(entity.getPrice())
                             .description(entity.getDescription())
                             .imageUrl(entity.getImageUrl())
                             .delYn(entity.getDelYn())
                             .build();
        }
    }

     // 도서 등록/수정
    @Data
    public static class BookRequest{

        private int bookId;
        @NotBlank(message="도서명은 필수 항목입니다.")
        private String title;
        @NotBlank(message="저자명은 필수 항목입니다.")
        private String author;
        @NotBlank(message="출판사는 필수 항목입니다.")
        private String publisher;
        private int price;
        private LocalDate publicationDate;
        private MultipartFile imageUrl;
        private String description;
        private BookStatus status;
        private int quantity;

        public BookEntity to(){

            BookEntity entity = new BookEntity();
            entity.setBookId(this.getBookId());
            entity.setTitle(this.getTitle());
            entity.setAuthor(this.getAuthor());
            entity.setPublisher(this.getPublisher());
            entity.setPrice(this.getPrice());
            entity.setPublicationDate(this.getPublicationDate());
            entity.setDescription(this.getDescription());
            entity.setStatus(this.getStatus() != null ? this.getStatus() : BookStatus.AVAILABLE);
            
            return entity;
        }

        public BookEntity toUpdate(BookEntity entity){

            entity.setTitle(this.getTitle());
            entity.setAuthor(this.getAuthor());
            entity.setPublisher(this.getPublisher());
            entity.setPublicationDate(this.getPublicationDate());
            entity.setPrice(this.getPrice());
            entity.setDescription(this.getDescription());
            
            return entity;
        }

    }

    
}
