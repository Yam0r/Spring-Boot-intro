package mate.academy.springintro.service.impl;

import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.mapper.UserMapper;
import mate.academy.springintro.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper{
    @Override
    public UserResponseDto toUserResponse(User savedUser) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(savedUser.getId());
        responseDto.setEmail(savedUser.getEmail());
        return responseDto;
    }
}