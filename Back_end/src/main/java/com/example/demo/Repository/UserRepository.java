package com.example.demo.Repository;

import java.util.Optional;

import com.example.demo.Entity.Userentity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Userentity, Long>{
    Optional<Userentity> findByEmail(String email);
}
