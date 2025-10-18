package it.exam.book_purple.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.order.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer>{

}
