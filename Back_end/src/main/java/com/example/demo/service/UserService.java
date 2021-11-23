package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import com.example.demo.Entity.Userentity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserInfoDto;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository; /*UserRepository클래스에 함수를 정희하고 UserService에서 사용*/
    @Autowired
    UserMapper userMapper;
    
    public List<String> findIdinDB(String receivedId) {
        List<String> result = userMapper.findIdinDB(receivedId);
        return result;
    }

    public void saveUserInfotoDB(UserInfoDto infoDto) { /*입력받은 아이디와 패스워드를 Mysql 에 저장 */        
        int userNum = infoDto.getUserNum();
        String id = infoDto.getId();
        String password = infoDto.getPassword();
        String auth = infoDto.getAuth();
        userMapper.saveUserInfo(userNum, id, password, auth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userentity user = userRepository.findById(username).
        orElseThrow(() -> new IllegalArgumentException("없음"));
        return new User(user.getUsername(), user.getPassword(), 
        Arrays.asList(new SimpleGrantedAuthority(user.getAuth())));
    }
    
}
