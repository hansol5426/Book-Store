package it.exam.book_purple.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.stock.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Integer>{

    @EntityGraph(attributePaths = {"book"})
    Optional<StockEntity> findByBook(BookEntity book);

}
