package com.management.system.service.impl;

import com.management.system.entities.Content;
import com.management.system.entities.Member;
import com.management.system.exception.ResourceNotFoundException;
import com.management.system.repository.ContentRepository;
import com.management.system.repository.MemberRepository;
import com.management.system.service.ContentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    @Value("${path.dirname}")
    private String imageDir;

    @Override
    @Transactional(rollbackOn = {IllegalStateException.class, IOException.class})
    public Content create(Content content, MultipartFile file) throws IllegalStateException, IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Could find content with id:"));
        content.setAuthor(member);
        try {
            content.setFileName(file.getOriginalFilename());
            Content entityToSave = contentRepository.save(content);
            if (file.getSize() > 0) {
                file.transferTo(new File(imageDir + entityToSave.getId() + "" + content.getFileName()));
            }

            return entityToSave;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Content viewContent(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could find content with id:" + id));
    }

    @Override
    public Content editContent(Content content, MultipartFile file) throws IllegalStateException, IOException {
        Content ct = viewContent(content.getId());
        ct.setContent(content.getContent());
        ct.setBrief(content.getBrief());
        ct.setTitle(content.getTitle());
        ct.setUpdateTime(content.getUpdateTime());

        try {
            Content enttiyToUpdate = contentRepository.save(ct);
            if (file.getSize() > 0) {
                file.transferTo(new File(imageDir + ct.getId() + "" + ct.getFileName()));
            }

            return enttiyToUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = {ResourceNotFoundException.class, Exception.class})
    public void deleteContent(Long id) {
        Content ct = viewContent(id);
        try {
            contentRepository.delete(ct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Content> getContents() {
        return contentRepository.findAll();
    }

    @Override
    public List<Content> getContents(String email) {
        return contentRepository.findByAuthor_email(email);
    }
}
