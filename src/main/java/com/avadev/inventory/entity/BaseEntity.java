package com.avadev.inventory.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        updateAt= LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
