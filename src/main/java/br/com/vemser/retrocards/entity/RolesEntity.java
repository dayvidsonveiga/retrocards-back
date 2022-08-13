package br.com.vemser.retrocards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "cargos")
@Getter
@Setter
public class RolesEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_seq")
    @SequenceGenerator(name = "cargo_seq", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "id_roles")
    private Integer idRoles;

    @Column(name = "role_name")
    private String roleName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.MERGE)
    private Set<UserEntity> users;

    public String getAuthority() {
        return roleName;
    }
}
