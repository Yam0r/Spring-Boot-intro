package mate.academy.springintro.security;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
}
