package br.com.vemser.retrocards.security;

import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> usuarioOptional = userService.findByEmailOptional(email);
        return usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inválido"));
    }
}