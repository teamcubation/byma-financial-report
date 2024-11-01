package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.application.port.in.AuthInPort;
import com.teamcubation.reportservice.application.port.in.RoleOutPort;
import com.teamcubation.reportservice.application.port.out.AuthOutPort;
import com.teamcubation.reportservice.application.service.Jwt.JwtService;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.model.user.Rol.Role;
import com.teamcubation.reportservice.domain.model.user.User;
import com.teamcubation.reportservice.domain.model.user.UserRole;
import com.teamcubation.reportservice.exceptionHandler.utils.MessageConstants;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService implements AuthInPort {

    public static final String ADMIN_EMAIL = "admin@gmail.com";
    private final AuthOutPort authOutPort;
    private final JwtService jwtService;
    private final RoleOutPort roleOutPort;

    private final AuthenticationManager authenticationManager;

    @Override
    public String login(User user) throws UserNotFoundException, UserEntityNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                ));
        User userFromDb = authOutPort.findByEmailIgnoreCase(user.getEmail());
        final String token = jwtService.generateToken(userFromDb);
        return token;
    }

    //TODO Implementar reglas de negocio para el registro. Similar al del crud de users
    @Override
    public String register(User user) throws UserDuplicateException, UserNotFoundException, UserEntityNotFoundException {

        if (authOutPort.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserDuplicateException(MessageConstants.DUPLICATED_EMAIL_USER);
        }

        if (authOutPort.existsByUserNameIgnoreCase(user.getUsername())) {
            throw new UserDuplicateException(MessageConstants.DUPLICATED_USERNAME_USER);
        }
        //Hardcodeamos el rol de admin
        if (user.getEmail().equalsIgnoreCase(ADMIN_EMAIL)) {
            user.setRoles(Set.of(Role.builder().role(UserRole.USER).build(), Role.builder().role(UserRole.ADMIN).build()));
            //user.setRoles(Set.of(Role.builder().role(UserRole.ADMIN).build()));

        }
        Set<Role> roles = user.getRoles().stream()
                .map(role -> {
                    Optional<Role> optionalRole = roleOutPort.findByRole(role.getRole());
                    return optionalRole.orElseGet(() -> roleOutPort.create(role));
                })
                .collect(Collectors.toSet());

        user.setRoles(roles);

        User createdUser = authOutPort.register(user);
        String token = jwtService.generateToken(createdUser);
        return token;
    }
}
