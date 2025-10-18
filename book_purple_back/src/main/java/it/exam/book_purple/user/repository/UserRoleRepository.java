package it.exam.book_purple.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.book_purple.user.entity.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String>{

}
