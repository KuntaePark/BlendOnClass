package com.blendonclass.dto;

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserAccountDto {
    private Long id;
    private String name;
    private String loginId;
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "비밀번호 확인을 입력하세요.")
    private String confirmPassword;
    private ROLE role;

    public static UserAccountDto from(Account account) {
        UserAccountDto dto = new UserAccountDto();
        dto.setId(account.getId());
        dto.setLoginId(account.getLoginId());
        dto.setName(account.getName());
        dto.setEmail(account.getEmail());
        dto.setPassword(account.getPassword());
        dto.setRole(account.getRole());
        return dto;
    }

    public Account toAccount() {
        Account account = new Account();
        account.setId(id);
        account.setLoginId(loginId);
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(role);
        return account;
    }
}
