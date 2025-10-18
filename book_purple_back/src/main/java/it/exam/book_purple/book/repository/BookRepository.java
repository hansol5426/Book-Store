package it.exam.book_purple.book.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import it.exam.book_purple.book.dto.BookProjection;
import it.exam.book_purple.book.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity>{

    Page<BookEntity> findByTitleContaining(String title, Pageable pageable);
    Page<BookEntity> findByAuthorContaining(String author, Pageable pageable);

    // 도서 조회(판매가능한 도서만)
    @Query(value = """
                select *
                from books b 
                where b.status ='AVAILABLE'
            """,
            nativeQuery = true)
    Page<BookEntity> findAllStatus(Pageable pageable);

    // 베스트셀러
    @Query(value = """
                select b.book_id,
                    b.title,
                    b.image_url,
                    sum(oi.quantity) as total_quantity
                from books b 
                    join order_item oi on b.book_id = oi.book_id
                where b.del_yn = 'N'
                group by b.book_id, b.title, b.image_url
                order by total_quantity desc
                limit 5;
            """,
            nativeQuery = true)
    List<BookProjection> findBestsellers();

    // 새로나온도서
    @Query(value = """
                select book_id,
                    title,
                    image_url
                from books
                where del_yn = 'N'
                order by create_date desc 
                limit 5
            """,
            nativeQuery = true)
    List<BookProjection> findNewBooks();

}
