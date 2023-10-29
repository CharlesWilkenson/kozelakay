package com.management.system.config;

import com.management.system.repository.MemberRepository;
import com.management.system.repository.RoleRepository;
import com.management.system.service.impl.MemberServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public MemberServiceImpl memberService(MemberRepository memberRepository, PasswordEncoder bcryptEncoder, RoleRepository roleRepository) {
        return new MemberServiceImpl(memberRepository, bcryptEncoder, roleRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
