package mate.academy.springintro.controller;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.user.UserLoginRequestDto;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.exception.RegistrationException;
import mate.academy.springintro.security.AuthenticationService;
import mate.academy.springintro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto registerUser(@RequestBody UserRegistrationRequestDto requestDto)  // Измените @RequestParam на @RequestBody
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
