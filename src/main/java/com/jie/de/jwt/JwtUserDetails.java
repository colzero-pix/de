package com.jie.de.jwt;

import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户:" + name));

        //检验权限
        String role = "admin".equals(user.getUsername()) ? "ROLE_ADMIN" : "ROLE_USER";

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(role)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}