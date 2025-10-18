package it.exam.book_purple.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.exam.book_purple.order.entity.CartItemEntity;
import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer>{

    @Modifying
    @Transactional
    @Query("""    
            delete 
            from CartItemEntity c 
            where c.citemId in :citemIds
            """)
    void deleteAllByCitemIdIn(@Param("citemIds") List<Integer> citemIds);

                            

}
