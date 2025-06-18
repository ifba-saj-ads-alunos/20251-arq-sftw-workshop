package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.application.exceptions.ResourceNotFoundException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
import br.ifba.ads.workshop.core.domain.models.enums.UserRole;
import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaAccessLevelRepository;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapperService {
    private final JpaAccessLevelRepository accessLevelRepository;
    private final JpaUserRoleRepository userRoleRepository;

    public UserRoleEntity findUserRoleEntity(UserRole userRole) {
        if (userRole == null) return null;
        return userRoleRepository.findByRoleAndDeletedIsFalse(userRole)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserRole não encontrado: " + userRole));
    }

    public AccessLevelEntity findAccessLevelEntity(AccessLevel accessLevel) {
        if (accessLevel == null) return null;
        return accessLevelRepository.findByLevelAndDeletedIsFalse(accessLevel)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "AccessLevel não encontrado: " + accessLevel));
    }
}
