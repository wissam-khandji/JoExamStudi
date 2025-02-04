package com.JOExamStudi.JOExamStudi.security;

import com.JOExamStudi.JOExamStudi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.JOExamStudi.JOExamStudi.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<User> optionalUser = userRepository.findByEmail(email);
       if (!optionalUser.isPresent()) {
           throw new UsernameNotFoundException("User not found with email: " + email);
       }
       return new CustomUserDetails(optionalUser.get());
    }
}
