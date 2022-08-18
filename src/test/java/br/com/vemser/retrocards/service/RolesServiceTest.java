package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.entity.UserEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.RolesRepository;
import br.com.vemser.retrocards.service.RolesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RolesServiceTest {

    @InjectMocks
    private RolesService rolesService;

    @Mock
    private RolesRepository rolesRepository;

    @Test
    public void testFindByRoleName() throws NegociationRulesException {
        //setup
        RolesEntity rolesEntity = getRolesEntity();
        when(rolesRepository.findByRoleName(anyString())).thenReturn(Optional.of(rolesEntity));

        //act
        RolesEntity roleRecovery = rolesService.findByRoleName(rolesEntity.getRoleName());


        //asserts
        assertNotNull(roleRecovery);
        assertEquals(rolesEntity.getIdRoles(), roleRecovery.getIdRoles());
        assertEquals(rolesEntity.getRoleName(), roleRecovery.getRoleName());
        assertEquals(rolesEntity.getUsers(), roleRecovery.getUsers());
    }

    private static RolesEntity getRolesEntity() {
        RolesEntity rolesEntity = new RolesEntity();
        rolesEntity.setIdRoles(1);
        rolesEntity.setRoleName("ROLE_FACILITATOR");
        rolesEntity.setUsers(Set.of(getUserEntity()));
        return rolesEntity;
    }

    private static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.setName("Willian");
        userEntity.setEmail("willian@gmail.com");
        userEntity.setPass("123");
        return userEntity;
    }
}
