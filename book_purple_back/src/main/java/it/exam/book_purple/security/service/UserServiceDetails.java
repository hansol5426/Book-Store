package it.exam.book_purple.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.exam.book_purple.security.dto.UserSecureDTO;
import it.exam.book_purple.user.entity.UserEntity;
import it.exam.book_purple.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceDetails implements UserDetailsService{
  
    private final UserRepository userRepository;
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user =
            userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

        if("Y".equals(user.getDelYn())){
            throw new UsernameNotFoundException("탈퇴한 계정입니다.");
        }

        return new UserSecureDTO(user.getUserId(), user.getUserName(),
                        user.getPassword(), user.getRole().getRoleId());

    }

}
