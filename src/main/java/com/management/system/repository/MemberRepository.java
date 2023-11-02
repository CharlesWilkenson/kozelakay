package com.management.system.repository;

import com.management.system.entities.Member;
import com.management.system.exception.AlreadyExistsException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
   // Optional<Member> findByEmailAndActiveTrue(String email);
    Boolean existsByEmail(String email);
}
