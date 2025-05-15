package com.blendonclass.constant;

import lombok.Getter;

@Getter
public enum ROLE {
    STUDENT("ROLE_STUDENT", "학생"),
    TEACHER("ROLE_TEACHER", "교사"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String role;
    private final String roleName;
    private ROLE(final String role, final String roleName) {
        this.role = role;
        this.roleName = roleName;
    }
}
