package com.springboot.prompthub.repository.impl;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.BaseEntity;
import com.springboot.prompthub.repository.SoftDelete;
import com.springboot.prompthub.utils.AppConstant;
import com.springboot.prompthub.utils.ApplicationContextProvider;
import com.springboot.prompthub.utils.UserPrincipal;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class SoftDeleteImpl implements SoftDelete {
    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationContext applicationContext;

    public SoftDeleteImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Transactional
    public void softDelete(BaseEntity baseEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setDeletedAt(new Date());

        if(authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            baseEntity.setDeletedBy(userPrincipal.getUser());
        }

        try{
            JpaRepository repository = applicationContext.getBean(baseEntity.getClass().getSimpleName() + "Repository", JpaRepository.class);
            entityManager.merge(baseEntity);
            entityManager.flush();
            repository.delete(baseEntity);
        }
        catch(Exception ex) {
            throw new APIException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    AppConstant.ERROR_API_SOFT_DELETE_FAIL);
        }
    }
}
