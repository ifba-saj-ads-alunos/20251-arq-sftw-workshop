package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.domain.exception.UnauthorizedException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.web.configs.TokenMainInfo;

import java.util.UUID;

public abstract class BaseController {

    protected void validateUser(TokenMainInfo tokenInfo, UUID userId) {
        if (tokenInfo == null || tokenInfo.userId() == null) {
            throw new UnauthorizedException("Access denied: User not authenticated.");
        }

        if (!tokenInfo.userId().equals(userId)) {
            throw new UnauthorizedException("Access denied: User ID does not match with token.");
        }
    }


    protected void verifyIfUserIsAdmin(TokenMainInfo tokenInfo) {
        if (tokenInfo.accessLevel() != AccessLevelType.ADMIN) {
            throw new UnauthorizedException("Access denied: Admin privileges required.");
        }
    }
}
