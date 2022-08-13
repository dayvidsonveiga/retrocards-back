package br.com.vemser.retrocards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_cargo",
            joinColumns = @JoinColumn(name = "id_roles"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<UserEntity> users;

    public String getAuthority() {
        return roleName;
    }
}
