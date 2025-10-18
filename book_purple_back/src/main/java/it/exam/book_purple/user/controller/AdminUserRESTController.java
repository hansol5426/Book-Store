package it.exam.book_purple.user.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.exam.book_purple.common.dto.ApiResponse;
import it.exam.book_purple.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminUserRESTController {

    private final AdminUserService userService;

    // 사용자리스트
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserList(@PageableDefault(size=10, page=0,
                          sort = {"createDate", "userId"}, direction = Direction.DESC) Pageable pageable) throws Exception{
        Map<String,Object> resultMap = userService.getUserList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(resultMap));                    
    }

}
