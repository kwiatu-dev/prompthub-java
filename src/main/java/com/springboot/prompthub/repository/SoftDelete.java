package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.BaseEntity;

public interface SoftDelete {
    void softDelete(BaseEntity entity);
}
