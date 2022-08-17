package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.page.PageDTO;
import br.com.vemser.retrocards.dto.user.*;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.enums.UserType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PageDTO<UserDTO> listAll(Integer pagina, Integer registro) throws NegociationRulesException {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<UserEntity> page = userRepository.findAll(pageRequest);
        if (!page.isEmpty()) {
            List<UserDTO> userDTOS = page.getContent().stream()
                    .map(this::entityToDTO)
                    .toList();
            return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, registro, userDTOS);
        } else {
            throw new NegociationRulesException("Não foi encontrado nenhum kudo card associado ao kudo box.");
        }
    }

    public UserNomeEmailDTO findUserWithNameAndEmail(Integer idUser) throws NegociationRulesException {
        UserEntity userEntity = findById(idUser);
        return entityToNomeEmailDTO(userEntity);
    }

    // Util

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

    public UserNomeEmailDTO entityToNomeEmailDTO(UserEntity userEntity) {
        UserNomeEmailDTO userNomeEmailDTO = objectMapper.convertValue(userEntity, UserNomeEmailDTO.class);
        return userNomeEmailDTO;
    }
}
