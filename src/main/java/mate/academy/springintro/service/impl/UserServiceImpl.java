package mate.academy.springintro.service.impl;

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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Can't register user");
        }

        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Role userRole = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() ->
                        new IllegalStateException("Role USER not found in the database"));

        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
