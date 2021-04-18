package com.example.tp2.security;

import com.example.tp2.entities.Users;
import com.example.tp2.entities.Users_roles;
import com.example.tp2.repositories.UsersRepository;
import com.example.tp2.repositories.UsersRolesRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UsersRepository userRepository;
    private UsersRolesRepository usersRolesRepository;

    public UserPrincipalDetailsService(UsersRepository userRepository, UsersRolesRepository usersRolesRepository) {
        this.userRepository = userRepository;
        this.usersRolesRepository = usersRolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s ) throws UsernameNotFoundException {
        Users user = this.userRepository.findByUsername(s);
        Users_roles users_roles=this.usersRolesRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user,users_roles);
        return userPrincipal;
    }
}
