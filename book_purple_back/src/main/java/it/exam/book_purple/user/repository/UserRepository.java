package it.exam.book_purple.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.exam.book_purple.user.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity>{

    @EntityGraph(attributePaths = {"role"})
    Page<UserEntity> findAll(Pageable pageable);

    @Query(value = """
                select u
                from UserEntity u 
                where u.userId = :userId 
                    and u.delYn = 'N'
            """)
    Optional<UserEntity> findByIdAndNotDeleted(@Param("userId") String userId);


    
}
