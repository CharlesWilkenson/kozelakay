package com.management.system.service.impl;

import com.management.system.entities.Member;
import com.management.system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid email"));
        UserDetails user = User.withUsername(member.getEmail())
                .password(member.getPassword())
                .authorities(getAuthorities(member)).build();
        return user;
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