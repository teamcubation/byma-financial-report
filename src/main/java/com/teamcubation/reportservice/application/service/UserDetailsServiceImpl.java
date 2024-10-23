package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.domain.model.user.UserAuthenticated;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        // Hardcodeamos el usuario "admin" con la contraseña "test1234"
        if ("admin".equals(username)) {
            // Aquí estamos encriptando la contraseña con BCrypt
            String encodedPassword = passwordEncoder.encode("test1234");

            UserEntity user = UserEntity.builder()
                    .id(1L)
                    .username(username)
                    .email("admin@teamcubation.com")
                    .password(encodedPassword)
                    .role(UserRole.ADMIN)
                    .build();

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            return new UserAuthenticated(user, authorities);
        }

        UserEntity userFromDb = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authoritiesFromDb = List.of(new SimpleGrantedAuthority("ROLE_" + userFromDb.getRole().name()));

        return new UserAuthenticated(userFromDb, authoritiesFromDb);

    }
}
