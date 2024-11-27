package mate.academy.springintro.service;

import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    ShoppingCartResponseDto getCartForUser(Long userId);

    ShoppingCartResponseDto addToCart(Long userId, AddToCartRequestDto requestDto);

    ShoppingCartResponseDto updateCartItem(Long userId, Long cartItemId,
                                           UpdateCartItemRequestDto requestDto);

    void removeCartItem(Long userId, Long cartItemId);
}
