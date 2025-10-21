package it.exam.book_purple.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{

    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    List<OrderEntity> findAllByUser_UserId(String userId);

}
