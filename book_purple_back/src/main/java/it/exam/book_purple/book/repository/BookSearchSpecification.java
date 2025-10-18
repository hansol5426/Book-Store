package it.exam.book_purple.book.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.exam.book_purple.book.dto.BookSearchDTO;
import it.exam.book_purple.book.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookSearchSpecification implements Specification<BookEntity>{

    private BookSearchDTO searchDTO;

    public BookSearchSpecification(BookSearchDTO searchDTO) {
        this.searchDTO = searchDTO;
    }

    @Override
    public Predicate toPredicate(Root<BookEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO != null && !StringUtils.isBlank(searchDTO.getSearchType()) && !StringUtils.isBlank(searchDTO.getSearchText())) {
            String likeText = "%" + searchDTO.getSearchText() +"%";
            if("title".equals(searchDTO.getSearchType())){
                predicates.add(cb.like(root.get("title"), likeText));
            }else if("author".equals(searchDTO.getSearchType())){
                predicates.add(cb.like(root.get("author"), likeText));
            }  
        }
        
        System.out.println("추가됐니?");
        predicates.add(cb.equal(root.get("status"), "AVAILABLE"));
                
        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates,  CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    
}
