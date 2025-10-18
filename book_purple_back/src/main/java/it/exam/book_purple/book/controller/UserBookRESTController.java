package it.exam.book_purple.book.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.book.dto.BookSearchDTO;
import it.exam.book_purple.book.dto.UserBookDTO;
import it.exam.book_purple.book.service.UserBookService;
import it.exam.book_purple.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserBookRESTController {

    private final UserBookService userBookService;
   
    // 도서리스트 조회
    @GetMapping("/book")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBookList(@PageableDefault(size = 10, page = 0, 
                                                                                sort = "title", direction = Direction.ASC) Pageable pageable) throws Exception{
        Map<String, Object> resultMap = userBookService.getBookList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 도서 검색
    @GetMapping("/book/search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBookSearch(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchText", required = false) String searchText,
            @PageableDefault(size = 10, page = 0, sort = "title", direction = Direction.ASC) Pageable pageable) throws Exception{

        BookSearchDTO searchDTO = new BookSearchDTO();
        searchDTO.setSearchType(searchType);      
        searchDTO.setSearchText(searchText);        

        Map<String, Object> resultMap = userBookService.getBookSearch(searchDTO, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 도서 상세 조회
    @GetMapping("/book/detail/{bookId}")
    public ResponseEntity<ApiResponse<UserBookDTO.BookDetail>> getBook(@PathVariable("bookId") int bookId) throws Exception{;

        UserBookDTO.BookDetail detail = userBookService.getBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(detail));
    }

    // 베스트셀러
    @GetMapping("/book/bestsellers")
    public ResponseEntity<ApiResponse<List<UserBookDTO.BookMain>>> getBestSellers() throws Exception{;

        List<UserBookDTO.BookMain> resultMap = userBookService.getBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 새로나온도서
    @GetMapping("/book/newBooks")
    public ResponseEntity<ApiResponse<List<UserBookDTO.BookMain>>> getNewBooks() throws Exception{;

        List<UserBookDTO.BookMain> detail = userBookService.getNewBooks();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(detail));
    }

    
}
