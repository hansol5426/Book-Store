package it.exam.book_purple.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.order.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Integer>{

    Optional<CartEntity> findByUser_UserId(String userId);

}
