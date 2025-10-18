package it.exam.book_purple.book.dto;

import lombok.Data;

@Data
public class BookSearchDTO {

    private String searchType;
    private String searchText;
}
