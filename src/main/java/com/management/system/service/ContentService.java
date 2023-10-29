package com.management.system.service;

import com.management.system.entities.Content;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    Content create(Content content, MultipartFile file)throws IllegalStateException, IOException;

    Content viewContent(Long id);

     Content editContent(Content content, MultipartFile file) throws IllegalStateException, IOException;

     void deleteContent(Long id);

     List<Content> getContents();

   // List<Content> getContents(Long authorId);
}
