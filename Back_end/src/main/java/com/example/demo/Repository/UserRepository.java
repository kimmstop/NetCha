package com.example.demo.Repository;

import java.util.Optional;
import com.example.demo.info.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findById(String user_id);
}
