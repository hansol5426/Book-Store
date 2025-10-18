package it.exam.book_purple.book.dto;

import java.time.LocalDate;

import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.book.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserBookDTO {

    // 도서 리스트
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserBookList{

        private int bookId;
        private String title;
        private String author;
        private String publisher;
        private int price;
        private LocalDate publicationDate;

        public static UserBookList of (BookEntity entity){

            return UserBookList.builder()
                               .bookId(entity.getBookId())
                               .title(entity.getTitle())
                               .author(entity.getAuthor())
                               .publisher(entity.getPublisher())
                               .price(entity.getPrice())
                               .publicationDate(entity.getPublicationDate())
                               .build();
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
        private BookStatus status;

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
                             .status(entity.getStatus())
                             .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BookMain{

        private int bookId;
        private String title;
        private String imageUrl;

        public static BookMain of (BookProjection projection){

            return BookMain.builder()
                           .bookId(projection.getBookId())
                           .title(projection.getTitle())
                           .imageUrl(projection.getImageUrl())
                           .build();
        }

    }
    
}
