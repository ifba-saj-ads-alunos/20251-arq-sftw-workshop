package br.ifba.ads.workshop.infra.configs;

import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaAccessLevelRepository;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaUserRoleRepository;

@Component
public class SeedDataInitializer {

    private final JpaAccessLevelRepository accessLevelRepository;
    private final JpaUserRoleRepository userRoleRepository;

    public SeedDataInitializer(JpaAccessLevelRepository accessLevelRepository,
                               JpaUserRoleRepository userRoleRepository) {
        this.accessLevelRepository = accessLevelRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        try {
            seedAccessLevels();
            seedUserRoles();
        } catch (Exception ex) {
            // Log and continue; do not fail application startup because of seeding issues
            System.err.println("SeedDataInitializer error: " + ex.getMessage());
        }
    }

    private void seedAccessLevels() throws Exception {
        if (accessLevelRepository.findByTypeAndDeletedIsFalse(AccessLevelType.ADMIN).isEmpty()) {
            var admin = new AccessLevelEntity(UUID.randomUUID(), AccessLevelType.ADMIN);
            // set description via reflection (no public setter)
            var descField = AccessLevelEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(admin, "Nível de Acesso de Administrador");
            // ensure base fields
            admin.setDeleted(false);
            admin.setCreatedAt(java.time.ZonedDateTime.now());
            admin.setUpdatedAt(java.time.ZonedDateTime.now());
            accessLevelRepository.save(admin);
        }

        if (accessLevelRepository.findByTypeAndDeletedIsFalse(AccessLevelType.USER).isEmpty()) {
            var user = new AccessLevelEntity(UUID.randomUUID(), AccessLevelType.USER);
            var descField = AccessLevelEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(user, "Nível de Acesso de Usuário Padrão");
            user.setDeleted(false);
            user.setCreatedAt(java.time.ZonedDateTime.now());
            user.setUpdatedAt(java.time.ZonedDateTime.now());
            accessLevelRepository.save(user);
        }
    }

    private void seedUserRoles() throws Exception {
        if (userRoleRepository.findByTypeAndDeletedIsFalse(UserRoleType.STUDENT).isEmpty()) {
            var student = new UserRoleEntity(UUID.randomUUID(), UserRoleType.STUDENT);
            var descField = UserRoleEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(student, "Papel de Usuário: Estudante");
            student.setDeleted(false);
            student.setCreatedAt(java.time.ZonedDateTime.now());
            student.setUpdatedAt(java.time.ZonedDateTime.now());
            userRoleRepository.save(student);
        }

        if (userRoleRepository.findByTypeAndDeletedIsFalse(UserRoleType.EMPLOYEE).isEmpty()) {
            var emp = new UserRoleEntity(UUID.randomUUID(), UserRoleType.EMPLOYEE);
            var descField = UserRoleEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(emp, "Papel de Usuário: Funcionário");
            emp.setDeleted(false);
            emp.setCreatedAt(java.time.ZonedDateTime.now());
            emp.setUpdatedAt(java.time.ZonedDateTime.now());
            userRoleRepository.save(emp);
        }

        if (userRoleRepository.findByTypeAndDeletedIsFalse(UserRoleType.TEACHER).isEmpty()) {
            var teacher = new UserRoleEntity(UUID.randomUUID(), UserRoleType.TEACHER);
            var descField = UserRoleEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(teacher, "Papel de Usuário: Professor");
            teacher.setDeleted(false);
            teacher.setCreatedAt(java.time.ZonedDateTime.now());
            teacher.setUpdatedAt(java.time.ZonedDateTime.now());
            userRoleRepository.save(teacher);
        }

        if (userRoleRepository.findByTypeAndDeletedIsFalse(UserRoleType.VISITOR).isEmpty()) {
            var visitor = new UserRoleEntity(UUID.randomUUID(), UserRoleType.VISITOR);
            var descField = UserRoleEntity.class.getDeclaredField("description");
            descField.setAccessible(true);
            descField.set(visitor, "Papel de Usuário: Visitante Externo");
            visitor.setDeleted(false);
            visitor.setCreatedAt(java.time.ZonedDateTime.now());
            visitor.setUpdatedAt(java.time.ZonedDateTime.now());
            userRoleRepository.save(visitor);
        }
    }
}
