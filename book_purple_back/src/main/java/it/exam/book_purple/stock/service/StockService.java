package it.exam.book_purple.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.exam.book_purple.common.dto.PageVO;
import it.exam.book_purple.stock.dto.StockDTO;
import it.exam.book_purple.stock.entity.StockEntity;
import it.exam.book_purple.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    // 재고리스트
    @Transactional
    public Map<String, Object> getStockList(Pageable pageable) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();
        Page<StockEntity> pageList = stockRepository.findAll(pageable);
        List<StockDTO.StockList> stockList = pageList.getContent().stream().map(StockDTO.StockList::of).toList();

        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(), (int)pageList.getTotalElements());

        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("page", pageList.getNumber());
        resultMap.put("content", stockList);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
    }

    // 재고상세
    @Transactional
    public StockDTO.StockDetail getStock(int stockId) throws Exception{
        StockEntity entity = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("찾는 재고가 없습니다."));
        return StockDTO.StockDetail.of(entity);
    }

    // 재고수정
    @Transactional
    public Map<String,Object> updateStock(StockDTO.StockRequest request) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();

        StockEntity entity = stockRepository.findById(request.getStockId()).orElseThrow(() -> new RuntimeException("찾는 재고가 없습니다."));

        if(request.getQuantity() < 0){
            throw new IllegalArgumentException("재고수량은 0 이상이어야 합니다.");
        }

        entity.getBook().getTitle();
        entity.setQuantity(request.getQuantity());
        entity.getBook().setStatus(request.getStatus());

        stockRepository.save(entity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;
    }

}
