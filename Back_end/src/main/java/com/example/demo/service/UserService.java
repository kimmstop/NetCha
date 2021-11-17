package com.example.demo.service;

import java.util.List;

import com.example.demo.Entity.Userentity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserInfoDto;

import org.springframework.beans.factory.annotation.Autowired;
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

   

    public void save(UserInfoDto infoDto) { /*입력받은 아이디와 패스워드를 Mysql 에 저장 */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
        Userentity u = new Userentity(infoDto.getUser_num(), infoDto.getId(), infoDto.getPassword(), infoDto.getAuth());
        userRepository.save(u);
        /*userRepository.save(Userentity.builder()
                .user_num(infoDto.getUser_num())
                .user_id(infoDto.getId())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword()).build()).getCode();*/
    }

    public void findAll() { /*현재 데이터베이스에 있는 모든 사용자 아이디 콘솔에 출력*/
        System.out.println("!!!");
        List<Userentity> user_list = userRepository.findAll();
        for(int i = 0; i <user_list.size(); i++){
            System.out.println(user_list.get(i).getId());
        }
        System.out.println("!!!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findById(username)){
            System.out.println("there is!");
        }
        else{
            System.out.println("None!");
        }
        return null;
    }
    
}
