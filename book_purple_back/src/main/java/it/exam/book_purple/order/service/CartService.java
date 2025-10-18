package it.exam.book_purple.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.exam.book_purple.book.entity.BookEntity;
import it.exam.book_purple.book.repository.BookRepository;
import it.exam.book_purple.order.dto.CartDTO;
import it.exam.book_purple.order.dto.CartItemDTO;
import it.exam.book_purple.order.entity.CartEntity;
import it.exam.book_purple.order.entity.CartItemEntity;
import it.exam.book_purple.order.repository.CartItemRepository;
import it.exam.book_purple.order.repository.CartRepository;
import it.exam.book_purple.security.dto.UserSecureDTO;

import it.exam.book_purple.user.entity.UserEntity;
import it.exam.book_purple.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    // 장바구니 조회
    @Transactional
    public Map<String,Object> getCart(UserSecureDTO user) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();

        CartEntity cartEntity = cartRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new RuntimeException("찾는 장바구니가 없습니다."));

        CartDTO.CartResponse response = CartDTO.CartResponse.of(cartEntity);

        int totalPrice = response.getCartItems().stream()
                             .mapToInt(CartItemDTO.CartItem::getTotalPrice)
                             .sum();

        resultMap.put("cart", response);
        resultMap.put("totalPrice", totalPrice);
        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;

    }

    // 장바구니 추가
    @Transactional
    public Map<String,Object> addCart(CartDTO.CartRequest request) throws Exception{

        Map<String,Object> resultMap = new HashMap<>();

        UserEntity userEntity = 
                userRepository.findById(request.getUserId())
                              .orElseThrow(()->new RuntimeException("찾는 사용자가 없습니다."));
        CartEntity cartEntity = 
                cartRepository.findByUser_UserId(request.getUserId())
                              .orElseGet(()-> {
                                CartEntity newCart =  new CartEntity();
                                newCart.setUser(userEntity);
                                return cartRepository.save(newCart);

        });

        BookEntity bookEntity = 
                bookRepository.findById(request.getBookId())
                              .orElseThrow(()->new RuntimeException("찾는 도서가 없습니다."));
        CartItemEntity cItemEntity = new CartItemEntity();
        cItemEntity.setCart(cartEntity);
        cItemEntity.setBook(bookEntity);
        cItemEntity.setQuantity(request.getQuantity());
        cartEntity.addCartItem(cItemEntity);
        cartRepository.save(cartEntity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;
    }

    // 장바구니 삭제
    @Transactional
    public Map<String,Object> deleteCart(List<Integer> cItems) throws Exception{

        Map<String, Object> resultMap = new HashMap<>();

        if (cItems != null && !cItems.isEmpty()) {
            cItemRepository.deleteAllById(cItems);
        }

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");   

        return resultMap;
    }
    
}
