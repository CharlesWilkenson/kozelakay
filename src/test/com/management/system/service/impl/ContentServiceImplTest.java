package com.management.system.service.impl;

import com.management.system.entities.Content;
import com.management.system.entities.Member;
import com.management.system.exception.ResourceNotFoundException;
import com.management.system.repository.ContentRepository;
import com.management.system.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContentServiceImplTest {
    @Mock
    private ContentRepository contentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ContentServiceImpl contentService;

    private static Content content;

    @BeforeAll
    static void initSharedResources() {
        Member member = new Member();
        member.setFirstname("wilki");
        member.setLastname("charli");
        member.setEmail("charleswilkenson2023@gmail.com");
        content = new Content();
        content.setAuthor(member);
        content.setContent("new Content");
        content.setCreatedDate(Instant.now());
        content.setTitle("War in Middle East");
    }

    @Test
    @DisplayName("Should return content when valid ID is provided")
    void viewContent_ValidId_ReturnsContent() {
        // Arrange
        Long id = 1L;
        Content content = new Content();
        content.setId(id);
        when(contentRepository.findById(id)).thenReturn(Optional.of(content));

        // Act
        Content result = contentService.viewContent(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(contentRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when content ID is not found")
    void viewContent_InvalidId_ThrowsException() {
        // Arrange
        Long id = 99L;
        when(contentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            contentService.viewContent(id);
        });
    }

    @Test
    @DisplayName("Should return a list of all contents")
    void getContents_ReturnsList() {
        // Arrange
        List<Content> mockList = Arrays.asList(new Content(), new Content());
        when(contentRepository.findAll()).thenReturn(mockList);

        // Act
        List<Content> result = contentService.getContents();

        // Assert
        assertEquals(2, result.size());
        verify(contentRepository).findAll();
    }

    @Test
    @DisplayName("Should return contents filtered by author email")
    void getContentsByEmail_ReturnsFilteredList() {
        // Arrange
        String email = "test@example.com";
        List<Content> mockList = List.of(new Content());
        when(contentRepository.findByAuthor_email(email)).thenReturn(mockList);

        // Act
        List<Content> result = contentService.getContents(email);

        // Assert
        assertFalse(result.isEmpty());
        verify(contentRepository).findByAuthor_email(email);
    }
}
