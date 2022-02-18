package com.unitech.service;

import com.unitech.dao.entity.UserEntity;
import com.unitech.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByPin(pin);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found in db");
        }

        UserEntity userEntity = optionalUser.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new User(userEntity.getPin(), userEntity.getPassword(), authorities);
    }
}
