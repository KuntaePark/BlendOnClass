package com.blendonclass.constant;

import lombok.Getter;

@Getter
public enum ROLE {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
    private ROLE(final String role) {
        this.role = role;
    }


}
