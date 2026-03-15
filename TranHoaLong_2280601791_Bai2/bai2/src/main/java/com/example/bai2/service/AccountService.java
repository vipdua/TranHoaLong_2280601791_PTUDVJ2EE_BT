package com.example.bai2.service;

import com.example.bai2.model.Account;
import com.example.bai2.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm account trong database theo tên đăng nhập
        Account account = accountRepository.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: " + username));

        // Lấy danh sách vai trò của người dùng và chuyển đổi thành SimpleGrantedAuthority
        Set<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Trả về đối tượng User từ Spring Security, bao gồm thông tin tài khoản và quyền hạn
        return new org.springframework.security.core.userdetails.User(
                account.getLoginName(),
                account.getPassword(),
                authorities);
    }
}