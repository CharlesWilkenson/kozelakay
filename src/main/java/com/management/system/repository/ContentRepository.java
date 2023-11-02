package com.management.system.repository;

import com.management.system.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByAuthor_email(String email);
}
