package it.exam.book_purple.book.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.exam.book_purple.book.dto.AdminBookDTO;
import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.book.enums.BookStatus;
import it.exam.book_purple.book.repository.BookRepository;
import it.exam.book_purple.common.dto.PageVO;
import it.exam.book_purple.common.utils.FileUtils;
import it.exam.book_purple.stock.entity.StockEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminBookService {

    @Value("${server.file.upload.path}")
    private String filePath;

    private final BookRepository bookRepository;
    private final FileUtils fileUtils;

    // 도서리스트 조회
    @Transactional
    public Map<String, Object> getBookList(Pageable pageable) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();
        Page<BookEntity> pageList = bookRepository.findAll(pageable);
        List<AdminBookDTO.AdminBookList> bookList = pageList.getContent().stream().map(AdminBookDTO.AdminBookList::of).toList();

        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(),(int)pageList.getTotalElements());

        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("page", pageList.getNumber());
        resultMap.put("content", bookList);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
    }

    // 도서 상세 조회
    @Transactional
    public AdminBookDTO.BookDetail getBook(int bookId) throws Exception{
        BookEntity entity = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("찾는 도서가 없습니다."));
        return AdminBookDTO.BookDetail.of(entity);
    } 

    // 도서 등록
    @Transactional
    public Map<String, Object> createBook(AdminBookDTO.BookRequest request) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();

        if(request.getQuantity() < 0){
            throw new IllegalArgumentException("재고수량은 0 이상이어야 합니다.");
        }
        if(request.getPrice() < 0){
            throw new IllegalArgumentException("단가는 0 이상이어야 합니다.");
        }

        BookEntity bookEntity = request.to();        
        bookEntity.setDelYn("N");
        
        String imgUrl = "";
        
        if(request.getImageUrl() != null && !request.getImageUrl().isEmpty()){
            Map<String,Object> fileMap = fileUtils.uploadFiles(request.getImageUrl(), filePath);
            imgUrl = "/static/imgs/" + fileMap.get("storedFileName");
            bookEntity.setImageUrl(imgUrl);
        }
        
        StockEntity stockEntity = new StockEntity();
        stockEntity.setBook(bookEntity);
        stockEntity.setQuantity(request.getQuantity());

        bookEntity.getStocks().add(stockEntity);

        bookRepository.save(bookEntity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;
    }

    // 도서 수정
    @Transactional
    public Map<String, Object> updateBook(AdminBookDTO.BookRequest request) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();
        
        BookEntity entity = bookRepository.findById(request.getBookId()).orElseThrow(() -> new RuntimeException("찾는 도서가 없습니다."));
        
        if(request.getPrice() < 0){
            throw new IllegalArgumentException("단가는 0 이상이어야 합니다.");
        }

        request.toUpdate(entity);        

        if(request.getImageUrl() != null && !request.getImageUrl().isEmpty()){

            String oldImageUrl = entity.getImageUrl();
            if(oldImageUrl != null && !oldImageUrl.isEmpty()){
                String oldImgPath = filePath + oldImageUrl.substring(oldImageUrl.lastIndexOf("/")+1);
                File oldImg = new File(oldImgPath);
                if(oldImg.exists()){
                    oldImg.delete();
                }
            }
            
            Map<String,Object> fileMap = fileUtils.uploadFiles(request.getImageUrl(), filePath);
            String imgUrl = "/static/imgs/" + fileMap.get("storedFileName");
            entity.setImageUrl(imgUrl);
        }
            
        bookRepository.save(entity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;
    }

    // 도서 삭제
    @Transactional
    public Map<String,Object> deleteBook(int bookId) throws Exception{

        Map<String ,Object> resultMap = new HashMap<>();
        BookEntity entity = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("찾는 도서가 없습니다."));
        
        entity.setDelYn("Y");
        entity.setStatus(BookStatus.DISCONTINUED);

        
        List<StockEntity> stocks = entity.getStocks();
        for(StockEntity stock : stocks){
            stock.modifiedUpdate();
        }
        
        bookRepository.save(entity);
        
        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;   

    }

}
