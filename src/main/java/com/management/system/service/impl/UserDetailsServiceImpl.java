package com.management.system.service.impl;

import com.management.system.entities.Member;
import com.management.system.entities.Status;
import com.management.system.exception.AccountLockedException;
import com.management.system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

   // @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid email"));

            try {
                if(member.getStatus() == Status.INACTIVE)
                    throw new AccountLockedException("Your account is locked please contact system administrator");
            } catch (AccountLockedException e) {

            throw new RuntimeException(e);
        }
        return User.withUsername(member.getEmail())
                .password(member.getPassword())
                .authorities(getAuthorities(member)).build();
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Member user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().stream().map(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            return role;
        }).collect(Collectors.toSet());
        return authorities;
    }
}