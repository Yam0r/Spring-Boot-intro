package mate.academy.springintro.mapper;

import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.model.User;

public interface UserMapper {
    UserResponseDto toUserResponse(User savedUser);
}
