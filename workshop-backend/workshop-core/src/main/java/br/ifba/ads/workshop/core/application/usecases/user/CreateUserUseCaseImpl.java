package br.ifba.ads.workshop.core.application.usecases.user;
import br.ifba.ads.workshop.core.application.exceptions.InvalidOperationException;
import br.ifba.ads.workshop.core.application.exceptions.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.application.mappers.UserMapper;
import br.ifba.ads.workshop.core.application.ports.in.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.ports.out.PasswordEncoderPort;
import br.ifba.ads.workshop.core.application.ports.out.repositories.UserRepositoryPort;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.UserOutput;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderPort passwordEncoder;

    @Override
    public UserOutput execute(CreateUserCommand command)
            throws ResourceAlreadyExistsException, InvalidOperationException
    {
        verifyIfUserExists(command.email());
        var mappedUser = userMapper.toDomain(command);
        String encodedPassword = passwordEncoder.encode(mappedUser.getPassword());
        var userToPersist = new User(
                null,
                mappedUser.getName(),
                mappedUser.getEmail(),
                mappedUser.getUserRole(),
                AccessLevel.USER,
                encodedPassword
        );
        return userMapper.toOutput(userRepository.save(userToPersist));

    }

    private void verifyIfUserExists(String email) throws ResourceAlreadyExistsException {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException("Usuário com email " + email + " já existe.");
        }
    }
}
