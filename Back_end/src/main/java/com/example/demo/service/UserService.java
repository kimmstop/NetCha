package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository; /*UserRepository클래스에 함수를 정희하고 UserService에서 사용*/
    @Autowired
    UserMapper userMapper;
   

    public void saveUserInfotoDB(UserInfoDto infoDto) { /*입력받은 아이디와 패스워드를 Mysql 에 저장 */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
        Userentity newUser = new Userentity(infoDto.getUser_num(), infoDto.getId(), infoDto.getPassword(), infoDto.getAuth());
        userRepository.save(newUser);
    }

    public void findAllUserinDB() { /*현재 데이터베이스에 있는 모든 사용자 아이디 콘솔에 출력*/
        List<Userentity> user_list = userRepository.findAll();
        for(int i = 0; i <user_list.size(); i++){
            System.out.println(user_list.get(i).getId());
        }
    }
    public ArrayList<HashMap<String, Object>> findAll() {
        return userMapper.findAll();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userentity user = userRepository.findById(username).
        orElseThrow(() -> new IllegalArgumentException("없음"));
        return new User(user.getUsername(), user.getPassword(), 
        Arrays.asList(new SimpleGrantedAuthority(user.getAuth())));
    }
    
}
