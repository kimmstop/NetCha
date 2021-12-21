package com.example.demo.service;

import java.util.Arrays;
import java.util.Optional;
import com.example.demo.Repository.UserRepository;
import com.example.demo.info.UserInfoDto;
import com.example.demo.info.UserInfo;
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
    
    public boolean isReceivedIdDuplicate(String receivedId) {
        if(userRepository.existsById(receivedId))
            return true;
        else
            return false;
    }

    public String makeEncryptedPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(rawPassword);
        return encryptedPassword;
    }

    public void saveReceivedUserInfoToDB(UserInfoDto recieveUserInfo) {
        int userNum = recieveUserInfo.getUserNum();
        String id = recieveUserInfo.getId();
        String encryptedPassword = makeEncryptedPassword(recieveUserInfo.getPassword());
        String auth = recieveUserInfo.getAuth();
        UserInfo newUser = new UserInfo(userNum, id, encryptedPassword, auth);
        userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String requestedUserId) throws UsernameNotFoundException {
        Optional<UserInfo> requestedUser = userRepository.findById(requestedUserId);
        if(requestedUser.isEmpty()){
            throw new IllegalArgumentException("사용자 없음");
        }
        UserInfo authedUser = requestedUser.get();
        return new User(authedUser.getUsername(), authedUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority(authedUser.getAuth())));   
    }
}
