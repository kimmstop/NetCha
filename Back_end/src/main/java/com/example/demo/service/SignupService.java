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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignupService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    public List<String> findIdInDB(String receivedId) {
        List<String> result = userMapper.findIdinDB(receivedId);
        return result;
    }

    public boolean isReceivedIdDuplicate(String receivedId) {
        if(findIdInDB(receivedId).isEmpty())
            return false;
        else
            return true;
    }

    public String makeEncryptedPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(rawPassword);
        return encryptedPassword;
    }

    public void saveReceivedUserInfoToDB(UserInfoDto recieveUserInfo) {
        int userNum = recieveUserInfo.getUserNum();
        String id = recieveUserInfo.getId();
        String encryptedpassword = makeEncryptedPassword(recieveUserInfo.getPassword());
        String auth = recieveUserInfo.getAuth();
        userMapper.saveUserInfo(userNum, id, encryptedpassword, auth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userentity user = userRepository.findById(username).
        orElseThrow(() -> new IllegalArgumentException("없음"));
        return new User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getAuth())));
    }
}
