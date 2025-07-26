package br.ifba.ads.workshop.core.domain.service;

import br.ifba.ads.workshop.core.domain.exception.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.core.domain.repositories.AccessLevelRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRoleRepository;


public final class UserCreationDomainService {
    private final UserRepository repository;
    private final AccessLevelRepository accessLevelRepository;
    private final UserRoleRepository userRoleRepository;

    public UserCreationDomainService(
            UserRepository userRepository,
            AccessLevelRepository accessLevelRepository,
            UserRoleRepository userRoleRepository
    ){
        this.repository = userRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User createNewUser(
            String name,
            Email email,
            EncryptedPassword encryptedPassword,
            UserRoleType userRoleType
    ) throws InternalServerException, ResourceAlreadyExistsException {
        var userRole = userRoleRepository.findByType(userRoleType).orElseThrow(() ->
                new InternalServerException("The user role with name '" + name + "' does not exist in the database"));
        var accessLevel =  accessLevelRepository.findByType(AccessLevelType.USER).orElseThrow(() ->
                new InternalServerException("The access level with name '" + name + "' does not exist in the database"));
        verifyIfUserExists(email);
        var newUser = new User(name, email, userRole, accessLevel, encryptedPassword, null);
        repository.saveSafely(newUser);
        return newUser;
    }

    private void verifyIfUserExists(Email email) throws ResourceAlreadyExistsException {
        if(repository.findByEmail(email.value()).isPresent()) {
            throw new ResourceAlreadyExistsException("Usuário com email " + email.value() + " já existente.");
        }
    }
}
