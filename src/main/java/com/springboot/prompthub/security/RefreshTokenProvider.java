package com.springboot.prompthub.security;

import com.springboot.prompthub.exception.APIException;
import com.springboot.prompthub.models.entity.RefreshToken;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.repository.RefreshTokenRepository;
import com.springboot.prompthub.repository.UserRepository;
import com.springboot.prompthub.utils.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RefreshTokenProvider {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app-refreshtoken-expiration-milliseconds}")
    private long refreshTokenExpiration;

    @Value("${app-refreshtoken-ttl-days}")
    private int refreshTokenTTL;

    public RefreshTokenProvider(
            RefreshTokenRepository refreshTokenRepository) {

        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken() {
        Date now = new Date();

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .token(getUniqueRefreshTokenUUID())
                .expires(new Date(now.getTime() + refreshTokenExpiration))
                .createdAt(now)
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    String getUniqueRefreshTokenUUID(){
        String refreshTokenUUID = UUID.randomUUID().toString();

        if(refreshTokenRepository.existsByToken(refreshTokenUUID)) {
            return getUniqueRefreshTokenUUID();
        }

        return refreshTokenUUID;
    }

    public void revokeRefreshToken(
            RefreshToken refreshToken,
            RefreshToken replaceByToken,
            String reasonRevoked) {

        if(refreshToken.isActive()) {
            refreshToken.setRevokedAt(new Date());
            refreshToken.setReplaceByToken(replaceByToken);
            refreshToken.setReasonRevoked(reasonRevoked);
            refreshTokenRepository.save(refreshToken);
        }

        throw new APIException(
                HttpStatus.BAD_REQUEST,
                AppConstant.ERROR_REFRESH_TOKEN_REVOKE_FAILED
        );
    }

    public RefreshToken rotateRefreshToken(RefreshToken refreshToken) {
        if(refreshToken.isActive()){
            RefreshToken newRefreshToken = createRefreshToken();

            revokeRefreshToken(
                    refreshToken,
                    newRefreshToken,
                    AppConstant.MESSAGE_REFRESH_TOKEN_ROTATE);

            refreshTokenRepository.save(newRefreshToken);

            return newRefreshToken;
        }

        throw new APIException(
                HttpStatus.BAD_REQUEST,
                AppConstant.ERROR_REFRESH_TOKEN_ROTATE_FAILED
        );
    }

    public void removeObsoleteRefreshTokens(User user) {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUser(user);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -refreshTokenTTL);
        refreshTokens.removeIf(rt -> rt.getCreatedAt().before(calendar.getTime()));
        refreshTokenRepository.deleteAll(refreshTokens);
    }

    public void revokeDescendantRefreshTokens(RefreshToken refreshToken) {
        if(refreshToken.getReplaceByToken() != null) {
            Optional<RefreshToken> childRefreshToken = refreshTokenRepository.findByToken(
                    refreshToken.getReplaceByToken().getToken());

            if(childRefreshToken.isPresent()){
                if(childRefreshToken.get().isActive()) {
                    revokeRefreshToken(
                            childRefreshToken.get(),
                            null,
                            AppConstant.MESSAGE_REFRESH_TOKEN_REUSE_REVOKED_TOKEN);
                }

                revokeDescendantRefreshTokens(childRefreshToken.get());
            }
        }
    }






}
