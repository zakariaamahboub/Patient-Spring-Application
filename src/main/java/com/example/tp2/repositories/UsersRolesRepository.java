package com.example.tp2.repositories;


import com.example.tp2.entities.Users_roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRolesRepository extends JpaRepository<Users_roles,String> {
    Users_roles findByUsername(String username);
}
