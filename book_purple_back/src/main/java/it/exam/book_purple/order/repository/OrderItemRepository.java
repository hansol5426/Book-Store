package it.exam.book_purple.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.order.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer>{

}
