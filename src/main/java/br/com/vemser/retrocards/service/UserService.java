package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RolesService rolesService;

    public UserDTO create(UserCreateDTO userCreateDTO) throws NegociationRulesException {
        checkEmailExist(userCreateDTO.getEmail());
        UserEntity userEntity = createToEntity(userCreateDTO);
        userEntity.setRole(rolesService.findByRoleName("ROLE_MEMBER"));
        return entityToDTO(userRepository.save(userEntity));
    }

    public void changeRole(Integer idUser, UserType userType) throws NegociationRulesException {
        UserEntity userEntity = findById(idUser);
        if (userEntity.getRole().equals(UserType.FACILITATOR)) {
            userEntity.setRole(rolesService.findByRoleName(userType.getRoleName()));
            userRepository.save(userEntity);
        } else if (userEntity.getRole().equals(UserType.MEMBER)) {
            userEntity.setRole(rolesService.findByRoleName(userType.getRoleName()));
            userRepository.save(userEntity);
        } else {
            userEntity.setRole(rolesService.findByRoleName(userType.getRoleName()));
            userRepository.save(userEntity);
        }
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
        UserDTO userDTO = entityToDTO(findById(idLoggedUser));
        UserEntity byId = findById(idLoggedUser);
        userDTO.setRole(byId.getRole().getRoleName());
        return userDTO;
    }

    public List<UserNameEmailDTO> listUsersWithNameAndEmail() throws NegociationRulesException {
        if (!userRepository.findAll().isEmpty()) {
            return userRepository.findAll().stream()
                    .map(this::entityToNomeEmailDTO).toList();
        } else {
            throw new NegociationRulesException("Não há usuários a serem listados!");
        }
    }

    // Util's

    public Integer getIdLoggedUser() throws NegociationRulesException{
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal.toString().equals("anonymousUser")) {
            throw new NegociationRulesException("Nenhum usuário logado!");
        } else {
            return (Integer) principal;
        }
    }

    public Optional<UserEntity> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByEmail(String email) throws NegociationRulesException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NegociationRulesException("Email não registrado!"));
    }

    public UserEntity findById(Integer idUser) throws NegociationRulesException {
        return userRepository.findById(idUser).orElseThrow(() -> new NegociationRulesException("Usuário não encontrado!"));
    }

    public void checkEmailExist(String email) throws NegociationRulesException {
        if (findByEmailOptional(email).isPresent()) {
            throw new NegociationRulesException("Email já está sendo utilizado!");
        }
    }

    public Boolean checkPasswordIsCorrect(String passwordInput, String passwordDB) {
        if (passwordEncoder.matches(passwordInput, passwordDB)) {
            return true;
        } else {
            return false;
        }
    }

    public UserDTO entityToDTO(UserEntity usuarioEntity) {
        UserDTO userDTO = objectMapper.convertValue(usuarioEntity, UserDTO.class);
        userDTO.setRole(usuarioEntity.getRole().getRoleName());
        return userDTO;
    }

    public UserEntity createToEntity(UserCreateDTO userCreateDTO) {
        UserEntity usuarioEntity = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        usuarioEntity.setPass(passwordEncoder.encode(userCreateDTO.getPassword()));
        return usuarioEntity;
    }

    public UserNameEmailDTO entityToNomeEmailDTO(UserEntity userEntity) {
        UserNameEmailDTO userNameEmailDTO = objectMapper.convertValue(userEntity, UserNameEmailDTO.class);
        return userNameEmailDTO;
    }
}
