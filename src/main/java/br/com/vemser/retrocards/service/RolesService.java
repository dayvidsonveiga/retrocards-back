package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.entity.RolesEntity;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesEntity findByRoleName(String role) throws NegociationRulesException {
        return rolesRepository.findByRoleName(role)
                .orElseThrow(() -> new NegociationRulesException("Role not found"));
    }
}
