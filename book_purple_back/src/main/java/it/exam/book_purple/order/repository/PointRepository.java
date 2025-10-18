package it.exam.book_purple.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.order.entity.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, Integer>{

}
