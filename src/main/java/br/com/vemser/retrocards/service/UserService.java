package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserCreateDTO;
import br.com.vemser.retrocards.dto.user.UserDTO;
import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserDTO saveUser(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = createToEntity(userCreateDTO);
        return entityToDto(userRepository.save(userEntity));
    }

    public UserDTO getLoggedUser() throws NegociationRulesException {
        Integer idLoggedUser = getIdLoggedUser();
        UserDTO userDTO = entityToDto(findById(idLoggedUser));
        UserEntity byId = findById(idLoggedUser);
        userDTO.setRole(byId.getRole().getRoleName());
        return userDTO;
    }

    public Integer getIdLoggedUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (Integer) principal;
    }

    public UserLoginReturnDTO returnUserDTOWithToken(UserLoginDTO userLoginDTO, String token) {
        Optional<UserEntity> userEntity = findByEmail(userLoginDTO.getEmail());
        UserLoginReturnDTO userLoginReturnDTO = new UserLoginReturnDTO();
        userLoginReturnDTO.setName(userEntity.get().getName());
        userLoginReturnDTO.setRole(userEntity.get().getRole().getRoleName());
        userLoginReturnDTO.setToken(token);
        return userLoginReturnDTO;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findById(Integer idUser) throws NegociationRulesException {
        return userRepository.findById(idUser).orElseThrow(() -> new NegociationRulesException("Usuário não encontrado"));
    }

    public Boolean checkPasswordIsCorrect(String passwordInput, String passwordDB) {
        if (passwordEncoder.matches(passwordInput, passwordDB)) {
            return true;
        } else {
            return false;
        }
    }

    public UserEntity createToEntity(UserCreateDTO userCreateDTO) {
        UserEntity usuarioEntity = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        usuarioEntity.setPass(passwordEncoder.encode(userCreateDTO.getPassword()));
        return usuarioEntity;
    }

    public UserDTO entityToDto(UserEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UserDTO.class);
    }

}
