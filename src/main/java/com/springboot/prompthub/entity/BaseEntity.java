package com.springboot.prompthub.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity<UserEntity> implements Serializable {
    @Id
    @UuidGenerator
    public UUID id;

    @CreatedBy
    private UserEntity createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private UserEntity modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private UserEntity deletedBy;
    private LocalDateTime deletedAt;
}
