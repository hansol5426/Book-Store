package it.exam.book_purple.book.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import it.exam.book_purple.book.enums.BookStatus;
import it.exam.book_purple.common.domain.BaseEntity;
import it.exam.book_purple.stock.entity.StockEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="books")
public class BookEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private int price;
    private  String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Column( columnDefinition = "CHAR(1)")
    @ColumnDefault("N")
    private String delYn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<StockEntity> stocks = new ArrayList<>();


}
