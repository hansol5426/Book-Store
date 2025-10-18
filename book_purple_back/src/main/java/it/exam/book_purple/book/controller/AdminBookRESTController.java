package it.exam.book_purple.book.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.book.dto.AdminBookDTO;
import it.exam.book_purple.book.service.AdminBookService;
import it.exam.book_purple.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminBookRESTController {

    private final AdminBookService bookService;
   
    // 도서리스트 조회
    @GetMapping("/book")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBookList(@PageableDefault(size = 10, page = 0, 
                            sort = {"createDate", "bookId"}, direction = Direction.DESC) Pageable pageable) throws Exception{
        Map<String, Object> resultMap = bookService.getBookList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 도서 상세 조회
    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<AdminBookDTO.BookDetail>> getBook(@PathVariable("bookId") int bookId) throws Exception{;

        AdminBookDTO.BookDetail detail = bookService.getBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(detail));
    }

    // 도서 등록
    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Map<String,Object>>> createBook(@ModelAttribute AdminBookDTO.BookRequest request) throws Exception{

        Map<String, Object> resultMap = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 도서 수정
    @PutMapping("/book")
    public ResponseEntity<ApiResponse<Map<String,Object>>> updateBook(@ModelAttribute AdminBookDTO.BookRequest request) throws Exception{

        Map<String, Object> resultMap = bookService.updateBook(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 도서 삭제
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<Map<String,Object>>> deleteBook(@PathVariable("bookId") int bookId) throws Exception{
        
        Map<String,Object> resultMap = bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    
}
