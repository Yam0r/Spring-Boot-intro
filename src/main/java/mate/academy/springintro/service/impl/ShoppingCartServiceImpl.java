package mate.academy.springintro.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;
import mate.academy.springintro.mapper.CartItemMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.CartItemRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.repository.UserRepository;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getCartForUser(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping Cart not found for user "
                        + userId));

        return new ShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addToCart(Long userId, AddToCartRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> createShoppingCartForUser(user));

        Book book = new Book(); // Assume you have a way to get the book by ID
        book.setId(requestDto.getBookId());

        CartItem cartItem = cartItemRepository.findByShoppingCartAndBook(shoppingCart, book)
                .orElse(new CartItem(shoppingCart, book, requestDto.getQuantity()));

        cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return new ShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(Long userId,
                                                  Long cartItemId,
                                                  UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id "
                        + cartItemId));

        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return new ShoppingCartResponseDto(cartItem.getShoppingCart());
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id "
                        + cartItemId));

        cartItemRepository.delete(cartItem);
    }

    private ShoppingCart createShoppingCartForUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }
}

