package mate.academy.springintro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.exception.RegistrationException;
import mate.academy.springintro.mapper.UserMapper;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.UserRepository;
import mate.academy.springintro.rolerepository.Role;
import mate.academy.springintro.rolerepository.RoleRepository;
import mate.academy.springintro.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String NOT_REGISTRATION_EMAIL_MESSAGE = "Can't register user:";
    private static final String NOT_FOUND_ROLE = "Role USER not found in the database:";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(NOT_REGISTRATION_EMAIL_MESSAGE);
        }

        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role userRole = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() ->
                        new EntityNotFoundException(NOT_FOUND_ROLE));

        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
