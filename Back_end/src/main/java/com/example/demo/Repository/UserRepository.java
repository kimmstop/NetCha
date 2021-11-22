package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Userentity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<Userentity, Long>{
    List<Userentity> findAll();
    Optional<Userentity> findById(String user_id);
    /*Todo
    List<Userentity> getUserInfoById(String user_id);*/
}
