package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;

import java.util.UUID;

public class UserRole extends BaseModel {
    private final UserRoleType type;

    public UserRole(
            UUID id,
            UserRoleType type
    ) throws InternalServerException {
        super(id);
        this.type = type;
    }

    public boolean verifyUserRole(Email email, UserRoleType expectedRoleType) {
        if (expectedRoleType == null || email == null) {
            return false;
        }

        UserRoleType determinedRole = determineUserRole(email);
        boolean isTeacherEmployeeFlex = determinedRole == UserRoleType.EMPLOYEE && expectedRoleType == UserRoleType.TEACHER;

        return determinedRole.equals(expectedRoleType) || isTeacherEmployeeFlex;
    }

    public UserRoleType getType() {
        return type;
    }

    private UserRoleType determineUserRole(Email email) {
        if (email.isStudentEmail()) {
            return UserRoleType.STUDENT;
        }

        if (email.isIfbaEmail()) {
            return UserRoleType.EMPLOYEE;
        }

        return UserRoleType.VISITOR;
    }
}