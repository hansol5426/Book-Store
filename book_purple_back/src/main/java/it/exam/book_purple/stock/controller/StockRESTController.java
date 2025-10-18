package it.exam.book_purple.stock.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.stock.dto.StockDTO;
import it.exam.book_purple.stock.service.StockService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class StockRESTController {

    private final StockService stockService;

    // 재고리스트
    @GetMapping("/stock")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStockList(@PageableDefault(size=10, page=0, sort = {"createDate", "stockId"}, direction = Direction.DESC) Pageable pageable) throws Exception{
        Map<String,Object> resultMap = stockService.getStockList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }

    // 재고상세
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<ApiResponse<StockDTO.StockDetail>> getStock(@PathVariable("stockId") int stockId) throws Exception{
        StockDTO.StockDetail detail = stockService.getStock(stockId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(detail));
    }

    // 재고수정
    @PutMapping("/stock")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateStock(@RequestBody StockDTO.StockRequest request) throws Exception{
        Map<String,Object> resultMap = stockService.updateStock(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));
    }
}
