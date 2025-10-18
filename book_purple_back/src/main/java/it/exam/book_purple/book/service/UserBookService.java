package it.exam.book_purple.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.exam.book_purple.book.dto.BookSearchDTO;
import it.exam.book_purple.book.dto.UserBookDTO;
import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.book.repository.BookRepository;
import it.exam.book_purple.book.repository.BookSearchSpecification;
import it.exam.book_purple.common.dto.PageVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBookService {

    private final BookRepository bookRepository;

    // 도서리스트 조회
    @Transactional
    public Map<String, Object> getBookList(Pageable pageable) throws Exception{

        Page<BookEntity> pageList = bookRepository.findAllStatus(pageable);

        List<UserBookDTO.UserBookList> bookList = pageList.getContent().stream().map(UserBookDTO.UserBookList::of).toList();
        
        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(),(int)pageList.getTotalElements());
        
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("page", pageList.getNumber());
        resultMap.put("content", bookList);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
    }

    // 도서 검색
    @Transactional
    public Map<String, Object> getBookSearch(BookSearchDTO searchDTO, Pageable pageable) throws Exception{

        Page<BookEntity> page = null;

        if(!StringUtils.isBlank(searchDTO.getSearchType()) && !StringUtils.isBlank(searchDTO.getSearchText())){
            BookSearchSpecification searchSpecification = new BookSearchSpecification(searchDTO);
            page = bookRepository.findAll(searchSpecification,pageable);
        }else{
            BookSearchSpecification AllSpecification = new BookSearchSpecification(null);
            page = bookRepository.findAll(pageable);
            System.out.println(AllSpecification);
        }

        List<UserBookDTO.BookDetail> bookList = page.getContent().stream().map(UserBookDTO.BookDetail::of).toList();
        
        PageVO pageVO = new PageVO();
        pageVO.setData(page.getNumber(),(int)page.getTotalElements());
        
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", page.getTotalElements());
        resultMap.put("page", page.getNumber());
        resultMap.put("content", bookList);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
    }

    // 도서 상세 조회
    @Transactional
    public UserBookDTO.BookDetail getBook(int bookId) throws Exception{
        BookEntity entity = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("찾는 도서가 없습니다."));

        return UserBookDTO.BookDetail.of(entity);
    }

    // 베스트셀러
    @Transactional
    public List<UserBookDTO.BookMain> getBestSellers() throws Exception{
        return bookRepository.findBestsellers()
                             .stream()
                             .map(UserBookDTO.BookMain::of)
                             .toList();
    }

    // 새로나온도서
    @Transactional
    public List<UserBookDTO.BookMain> getNewBooks() throws Exception{
        return bookRepository.findNewBooks()
                             .stream()
                             .map(UserBookDTO.BookMain::of)
                             .toList();
    }

}
