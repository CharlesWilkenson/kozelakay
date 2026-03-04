package com.management.system.service.impl;

import com.management.system.entities.Member;
import com.management.system.entities.Status;
import com.management.system.exception.AccountLockedException;
import com.management.system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class UserDetailsServiceImplTest {
    @Mock
    private  MemberRepository memberRepository;


}