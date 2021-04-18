package com.example.tp2.repositories;


import com.example.tp2.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,String> {
    Users findByUsername(String username);
}
