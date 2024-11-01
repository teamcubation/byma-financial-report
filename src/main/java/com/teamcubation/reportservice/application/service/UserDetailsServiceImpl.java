package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.domain.model.user.UserAuthenticated;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.user.UserEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //ahora se hardcodea el user admin en el registro del authService

        //este llamado a userRepostory podria ser un call al servicio de autenticacion
        UserEntity userFromDb = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));
        //List<GrantedAuthority> authoritiesFromDb = List.of(new SimpleGrantedAuthority("ROLE_" + userFromDb.getRole().name()));
        List<GrantedAuthority> authoritiesFromDb = userFromDb.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name())).collect(Collectors.toCollection(ArrayList::new));
        return new UserAuthenticated(userFromDb, authoritiesFromDb);

    }
}
