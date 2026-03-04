package com.management.system.service.impl;


import com.management.system.entities.Member;
import com.management.system.entities.Status;
import com.management.system.exception.ResourceNotFoundException;
import com.management.system.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("Should find member by email")
    void findByEmail_ValidEmail_ReturnsMember() {
        String email = "test@example.com";
        Member member = new Member();
        member.setEmail(email);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        Member result = memberService.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when email not found")
    void findByEmail_InvalidEmail_ThrowsException() {
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.findByEmail("wrong@mail.com"));
    }

    @Test
    @DisplayName("Should toggle status from ACTIVE to INACTIVE")
    void changeStatus_FromActive_ToInactive() {
        // Arrange
        Long id = 1L;
        Member member = new Member();
        member.setId(id);
        member.setStatus(Status.ACTIVE);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        // Act
        Member result = memberService.changeStatus(id);

        // Assert
        assertEquals(Status.INACTIVE, result.getStatus());
        verify(memberRepository).save(member); // Verify the save method was called
    }

    @Test
    @DisplayName("Should toggle status from INACTIVE to ACTIVE")
    void changeStatus_FromInactive_ToActive() {
        // Arrange
        Long id = 1L;
        Member member = new Member();
        member.setId(id);
        member.setStatus(Status.INACTIVE);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        // Act
        Member result = memberService.changeStatus(id);

        // Assert
        assertEquals(Status.ACTIVE, result.getStatus());
        verify(memberRepository).save(member);
    }
}
