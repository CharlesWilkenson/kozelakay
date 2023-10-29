package com.management.system.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public class AbstractAuditingEntity {

    @CreatedDate
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    protected Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "UpdateTime")
    protected Instant updateTime = Instant.now();
}
