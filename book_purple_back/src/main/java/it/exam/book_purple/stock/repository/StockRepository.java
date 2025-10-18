package it.exam.book_purple.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.stock.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Integer>{

}
