package com.springboot.prompthub.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.*;
import com.springboot.prompthub.converter.SoftDeleteTypeConverter;
import com.springboot.prompthub.utils.UserPrincipal;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SoftDelete(strategy = SoftDeleteType.ACTIVE, columnName = "state", converter = SoftDeleteTypeConverter.class)
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @CreatedBy
    @JsonIgnore
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @LastModifiedBy
    @JsonIgnore
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Date modifiedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "deleted_by")
    private User deletedBy;

    @Column(name = "deleted_at")
    private Date deletedAt;
}
