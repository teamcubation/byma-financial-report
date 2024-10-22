package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.domain.model.user.UserAuthenticated;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //MockUserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //return new UserAuthenticated(user);

        // Hardcodeamos el usuario "admin" con la contraseña "test1234"
        if ("admin".equals(username)) {
            // Aquí estamos encriptando la contraseña con BCrypt
            String encodedPassword = passwordEncoder.encode("test1234");

            UserEntity user = UserEntity.builder()
                    .id(1L)
                    .username(username)
                    .email("admin@teamcubation.com")
                    .password(encodedPassword)
                    .build();
            return new UserAuthenticated(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
