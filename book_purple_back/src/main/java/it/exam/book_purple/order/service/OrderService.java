package it.exam.book_purple.order.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.exam.book_purple.book.repository.BookRepository;
import it.exam.book_purple.order.dto.OrderDTO;
import it.exam.book_purple.order.dto.OrderItemDTO;
import it.exam.book_purple.order.dto.OrderItemDTO.OrderItemRequest;
import it.exam.book_purple.order.entity.OrderEntity;
import it.exam.book_purple.order.entity.OrderItemEntity;
import it.exam.book_purple.order.entity.PaymentEntity;
import it.exam.book_purple.order.entity.PointEntity;
import it.exam.book_purple.order.enums.BookPoint;
import it.exam.book_purple.order.repository.CartItemRepository;
import it.exam.book_purple.order.repository.OrderItemRepository;
import it.exam.book_purple.order.repository.OrderRepository;
import it.exam.book_purple.order.repository.PaymentRepository;
import it.exam.book_purple.order.repository.PointRepository;
import it.exam.book_purple.security.dto.UserSecureDTO;
import it.exam.book_purple.user.entity.UserEntity;
import it.exam.book_purple.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository oItemRepository;
    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;

    // 주문내역 조회
    @Transactional
    public Map<String,Object> getOrder(UserSecureDTO user) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();

        List<OrderEntity> orderEntity = orderRepository.findAllByUser_UserId(user.getUserId());

        if(orderEntity.isEmpty()){
            throw new RuntimeException("찾는 주문내역이 없습니다.");
        }

        List<OrderDTO.OrderResponse> response = orderEntity.stream()
                                                           .map(OrderDTO.OrderResponse::of)
                                                           .toList();

        int totalPrice = response.stream()                         
                                 .flatMap(order -> order.getOrderItems().stream())       
                                 .mapToInt(OrderItemDTO.OrderItem::getTotalPrice)   
                                 .sum(); 

        resultMap.put("order", response);
        resultMap.put("totalPrice", totalPrice);
        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;

    }

    // 장바구니에서 구매
    @Transactional
    public Map<String,Object> orderCart(OrderDTO.OrderRequest request) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();

        UserEntity userEntity = userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("찾는 유저가 없습니다."));
        OrderEntity orderEntity = addOrder(userEntity, request.getOrderItems(), request.getTotalPrice());

        // 장바구니 삭제
        List<Integer> citemIds = request.getOrderItems()
                                        .stream()
                                        .map(item -> item.getCitemId())
                                        .toList();

        cItemRepository.deleteAllByCitemIdIn(citemIds);


        // 클라이언트 응답
        OrderDTO.OrderResponse response = OrderDTO.OrderResponse.of(orderEntity);
                
        resultMap.put("data", response);    
        resultMap.put("resultCode",200);
        resultMap.put("resultMsg","OK");

        return resultMap;
    }
    
    // 즉시 구매
    @Transactional
    public Map<String,Object> orderDirect(OrderDTO.OrderRequest request) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        
        UserEntity userEntity = userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("찾는 유저가 없습니다."));
        OrderEntity orderEntity = addOrder(userEntity, request.getOrderItems(), request.getTotalPrice());
        
        // 클라이언트 응답
        OrderDTO.OrderResponse response = OrderDTO.OrderResponse.of(orderEntity);
        
        resultMap.put("data", response);    
        resultMap.put("resultCode",200);
        resultMap.put("resultMsg","OK");
        
        return resultMap;
    }
    
    private OrderEntity addOrder(UserEntity userEntity, List<OrderItemRequest> items, int totalPrice){
        
        // 사용자포인트 차감
        if(userEntity.getPoint() < totalPrice){
            throw new RuntimeException("포인트가 부족합니다.\n가지고 계신 포인트는 " + userEntity.getPoint() + "Point 입니다.");
        }

        // 주문생성
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setStatus("Complete");
        orderRepository.save(orderEntity);
        
        // 주문저장
        List<OrderItemEntity> orderItems = items.stream().map(i -> {
            OrderItemEntity oItemEntity = new OrderItemEntity();
            oItemEntity.setOrder(orderEntity);
            oItemEntity.setBook(bookRepository.findById(i.getBookId()).orElseThrow(()-> new RuntimeException("찾는 도서가 없습니다.")));
            oItemEntity.setQuantity(i.getQuantity());
            oItemEntity.setPrice(i.getPrice());
            return oItemEntity;
        }).toList();
        oItemRepository.saveAll(orderItems);

        // 결제저장
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrder(orderEntity);
        paymentEntity.setPaymentMethod("POINT");
        paymentEntity.setPaidTime(LocalDateTime.now());
        paymentRepository.save(paymentEntity);

        // 포인트차감
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUser(userEntity);
        pointEntity.setAmount(totalPrice);
        pointEntity.setType(BookPoint.SPEND);
        pointEntity.setCreateDate(LocalDateTime.now());
        pointRepository.save(pointEntity);


        userEntity.setPoint(userEntity.getPoint() - totalPrice);
        userRepository.save(userEntity);

        return orderEntity;
    }
}
