package br.com.vemser.retrocards.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserType {

    FACILITATOR("ROLE_FACILITATOR"),
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    String roleName;

    UserType(String descricao) {
        this.roleName = descricao;
    }
}
