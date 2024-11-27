package mate.academy.springintro.service;

import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getCartForUser(Long userId);

    ShoppingCartResponseDto addToCart(Long userId, AddToCartRequestDto requestDto);

    ShoppingCartResponseDto updateCartItem(Long userId, Long cartItemId,
                                           UpdateCartItemRequestDto requestDto);

    void removeCartItem(Long userId, Long cartItemId);
}
