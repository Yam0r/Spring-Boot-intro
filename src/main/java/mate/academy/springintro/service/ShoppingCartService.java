package mate.academy.springintro.service;

import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getCartForUser(Long userId);

    ShoppingCartResponseDto addToCart(Long userId, AddToCartRequestDto requestDto);

    ShoppingCartResponseDto updateCartItem(Long userId, Long cartItemId,
                                           UpdateCartItemRequestDto requestDto);

    void removeCartItem(Long userId, Long cartItemId);
    
    ShoppingCart createShoppingCartForUser(User user);
}
