package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.UserCreateDTO;
import br.com.vemser.retrocards.dto.user.UserDTO;
import br.com.vemser.retrocards.dto.user.UserLoginDTO;
import br.com.vemser.retrocards.dto.user.UserLoginReturnDTO;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.UserType;
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
    private final RolesService rolesService;


    public UserDTO create(UserCreateDTO userCreateDTO, UserType userType) throws NegociationRulesException {
        checkEmailExist(userCreateDTO.getEmail());
        UserEntity userEntity = createToEntity(userCreateDTO);
        userEntity.setRole(rolesService.findByRoleName(userType.getRoleName()));
        return entityToDto(userRepository.save(userEntity));
    }

    public UserLoginReturnDTO login(UserLoginDTO userLoginDTO, String token) throws NegociationRulesException {
        UserEntity userEntity = findByEmail(userLoginDTO.getEmail());
        UserLoginReturnDTO userLoginReturnDTO = new UserLoginReturnDTO();
        userLoginReturnDTO.setName(userEntity.getName());
        userLoginReturnDTO.setRole(userEntity.getRole().getRoleName());
        userLoginReturnDTO.setToken(token);
        return userLoginReturnDTO;
    }

    public UserDTO getLoggedUser() throws NegociationRulesException {
        Integer idLoggedUser = getIdLoggedUser();
        UserDTO userDTO = entityToDto(findById(idLoggedUser));
        UserEntity byId = findById(idLoggedUser);
        userDTO.setRole(byId.getRole().getRoleName());
        return userDTO;
    }

    // Util

    public Integer getIdLoggedUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (Integer) principal;
    }

    public Optional<UserEntity> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByEmail(String email) throws NegociationRulesException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NegociationRulesException("Email is not register"));
    }

    public UserEntity findById(Integer idUser) throws NegociationRulesException {
        return userRepository.findById(idUser).orElseThrow(() -> new NegociationRulesException("User not found!"));
    }

    public void checkEmailExist(String email) throws NegociationRulesException {
        if (findByEmailOptional(email).isPresent()) {
            throw new NegociationRulesException("Email has already been registered!");
        }
    }

    public Boolean checkPasswordIsCorrect(String passwordInput, String passwordDB) {
        if (passwordEncoder.matches(passwordInput, passwordDB)) {
            return true;
        } else {
            return false;
        }
    }

    public UserDTO entityToDto(UserEntity usuarioEntity) {
        UserDTO userDTO = objectMapper.convertValue(usuarioEntity, UserDTO.class);
        userDTO.setRole(usuarioEntity.getRole().getRoleName());
        return userDTO;
    }

    public UserEntity createToEntity(UserCreateDTO userCreateDTO) {
        UserEntity usuarioEntity = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        usuarioEntity.setPass(passwordEncoder.encode(userCreateDTO.getPassword()));
        return usuarioEntity;
    }
}
