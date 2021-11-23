package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface UserMapper {
     List<String> findIdinDB(String id);
     void saveUserInfo(@Param("userNum") int userNum, @Param("id") String id, 
     @Param("password") String passowrd, @Param("auth") String auth);
}   