package it.exam.book_purple.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.exam.book_purple.common.dto.PageVO;
import it.exam.book_purple.user.dto.UserDTO;
import it.exam.book_purple.user.entity.UserEntity;
import it.exam.book_purple.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    // 사용자리스트
    @Transactional
    public Map<String,Object> getUserList(Pageable pageable) throws Exception{

        Map<String, Object> resultMap = new HashMap<>();
        Page<UserEntity> pageList = userRepository.findAll(pageable);
        List<UserDTO.UserList> userList = pageList.getContent().stream().map(UserDTO.UserList::of).toList();

        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(), (int)pageList.getTotalElements());

        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("page", pageList.getNumber());
        resultMap.put("content", userList);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
        
    }

}
