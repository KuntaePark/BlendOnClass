package com.blendonclass.service;

import com.blendonclass.entity.Account;
import com.blendonclass.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId));

        return User.builder()
                    .username(account.getId().toString())
                    .password(account.getPassword())
                    .roles(account.getRole().toString())
                    .build();
    }
}
