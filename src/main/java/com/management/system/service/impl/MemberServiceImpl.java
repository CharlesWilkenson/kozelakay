package com.management.system.service.impl;

import com.management.system.entities.Authority;
import com.management.system.entities.Member;
import com.management.system.entities.Role;
import com.management.system.exception.AlreadyExistsException;
import com.management.system.exception.PasswordsNotMatchException;
import com.management.system.exception.ResourceNotFoundException;
import com.management.system.repository.MemberRepository;
import com.management.system.repository.RoleRepository;
import com.management.system.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder bcryptEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void register(Member member) throws AlreadyExistsException, PasswordsNotMatchException {
        if (Boolean.TRUE.equals(memberRepository.existsByEmail(member.getEmail()))) throw new AlreadyExistsException("This email already used");
        if(!member.getPassword().equals(member.getRePassword())) throw new PasswordsNotMatchException("The passwords don't match");

        member.setPassword(bcryptEncoder.encode(member.getPassword()));
        final HashSet<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(Authority.MEMBER);

        if (role == null) {
            role =  Role.builder()
                    .name(Authority.MEMBER)
                    .build();
            roleRepository.save(role);
        }
        roles.add(role);
        member.setRoles(roles);
        memberRepository.save(member);
    }

    @Override
    public void edit(Member member) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isEmpty()) throw new ResourceNotFoundException("User not found");
        memberOptional.get().setFirstname(member.getFirstname());
        memberOptional.get().setLastname(member.getLastname());
        memberOptional.get().setDescription(member.getDescription());
        memberOptional.get().setPhone(member.getPhone());
        memberRepository.save(memberOptional.get());
    }

    @Override
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(()->  new ResourceNotFoundException("Member not found"));
    }

}
