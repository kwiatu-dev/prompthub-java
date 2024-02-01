package com.springboot.prompthub.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @UuidGenerator
    private String id;

    @Column(unique = true)
    private String token;
    private Date expires;
    private Date createdAt;
    private Date revokedAt;

    @ManyToOne
    @JoinColumn(name = "replace_by_token", referencedColumnName = "token")
    private RefreshToken replaceByToken;
    private String reasonRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public boolean isExpired(){
        return new Date().getTime() >= expires.getTime();
    }

    public boolean isRevoked(){
        return revokedAt != null;
    }

    public boolean isActive(){
        return !isRevoked() && !isExpired();
    }
}
