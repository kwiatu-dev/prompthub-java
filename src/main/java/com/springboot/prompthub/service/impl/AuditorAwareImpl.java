package com.springboot.prompthub.service.impl;

import java.util.Optional;

import com.springboot.prompthub.utils.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.springboot.prompthub.models.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            User user = userPrincipal.getUser();
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
