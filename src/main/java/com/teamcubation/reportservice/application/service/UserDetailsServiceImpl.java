package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.domain.model.user.UserAuthenticated;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //ahora se hardcodea el user admin en el registro del authService

        //este llamado a userRepostory podria ser un call al servicio de autenticacion
        UserEntity userFromDb = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authoritiesFromDb = List.of(new SimpleGrantedAuthority("ROLE_" + userFromDb.getRole().name()));

        return new UserAuthenticated(userFromDb, authoritiesFromDb);

    }
}
