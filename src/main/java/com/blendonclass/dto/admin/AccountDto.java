package com.blendonclass.dto.admin;

/*
   계정 생성/수정용
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter
public class AccountDto {
    private Long id = null;

    @NotBlank(message="이름은 필수 입력입니다.")
    @Length(max=32, message = "이름은 최대 32자입니다.")
    private String name;

    @NotBlank(message="이메일은 필수 입력입니다.")
    @Email(message="이메일 형식이 올바르지 않습니다.")
    @Length(max = 128, message = "이름은 최대 128자입니다.")
    private String email;

    @NotNull(message="역할을 선택해야 합니다.")
    private ROLE role;

    public static AccountDto from(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setEmail(account.getEmail());
        dto.setRole(account.getRole());
        return dto;
    }

    public Account to() {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setEmail(email);
        account.setRole(role);
        return account;
    }
}
