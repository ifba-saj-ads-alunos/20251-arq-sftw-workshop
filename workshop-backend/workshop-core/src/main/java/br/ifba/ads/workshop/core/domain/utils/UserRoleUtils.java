package br.ifba.ads.workshop.core.domain.utils;

import br.ifba.ads.workshop.core.domain.models.enums.UserRole;

import java.util.regex.Pattern;

public final class UserRoleUtils {

    private static final String IFBA_EMAIL_DOMAIN = "@ifba.edu.br";
    private static final Pattern STUDENT_EMAIL_PATTERN = Pattern.compile(
            "^\\d{4}\\d{1,2}T[A-Za-z]+SAJ\\d+@ifba\\.edu\\.br$"
    );

    private UserRoleUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static boolean isIfbaEmail(String email) {
        return email != null && email.endsWith(IFBA_EMAIL_DOMAIN);
    }

    public static boolean isStudentEmail(String email) {
        if (email.isEmpty() || !isIfbaEmail(email)) {
            return false;
        }
        return email.matches(STUDENT_EMAIL_PATTERN.pattern());
    }

    private static UserRole determineUserRole(String email) {
        if (isStudentEmail(email)) {
            return UserRole.STUDENT;
        }

        if(isIfbaEmail(email)) {
            return UserRole.EMPLOYEE;
        }

        return UserRole.VISITOR;
    }

    public static boolean verifyUserRole(String email, UserRole userRole) {
        if (userRole == null) {
            return false;
        }

        UserRole determinedUserRole = determineUserRole(email);
        boolean isTeacherEmployeeFlex = determinedUserRole == UserRole.EMPLOYEE && userRole == UserRole.TEACHER;

        return determinedUserRole.equals(userRole) || isTeacherEmployeeFlex;
    }
}
