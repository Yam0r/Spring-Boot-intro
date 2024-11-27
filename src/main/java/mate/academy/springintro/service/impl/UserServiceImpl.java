package mate.academy.springintro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.CartItemResponseDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.exception.RegistrationException;
import mate.academy.springintro.mapper.ShoppingCartMapper;
import mate.academy.springintro.mapper.UserMapper;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.CartItemRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.repository.UserRepository;
import mate.academy.springintro.repository.book.BookRepository;
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
    private static final String NOT_FOUND_ROLE = "Role %s not found in the database:";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(NOT_REGISTRATION_EMAIL_MESSAGE);
        }

        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        String roleName = Role.RoleName.USER.name();
        Role userRole = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_ROLE,
                        roleName)));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        createShoppingCartForUser(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public ShoppingCartResponseDto getCartForUser(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping Cart not found for user "
                        + userId));
        return toShoppingCartDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addToCart(Long userId, AddToCartRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> createShoppingCartForUser(user));
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id "
                        + requestDto.getBookId()));
        CartItem cartItem = cartItemRepository.findByShoppingCartAndBook(shoppingCart, book)
                .orElse(new CartItem(shoppingCart, book, requestDto.getQuantity()));

        cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateCartItem(Long userId, Long cartItemId,
                                                  UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id "
                        + cartItemId));

        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return toShoppingCartDto(cartItem.getShoppingCart()); //the same, use mapper
    }

    @Override
    @Transactional
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

    private ShoppingCartResponseDto toShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto dto = new ShoppingCartResponseDto();
        dto.setId(shoppingCart.getId());
        dto.setUserId(shoppingCart.getUser().getId());
        dto.setCartItems(
                shoppingCart.getCartItems().stream()
                        .map(this::toCartItemDto)
                        .toList()
        );
        return dto;
    }

    private CartItemResponseDto toCartItemDto(CartItem cartItem) {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(cartItem.getId());
        dto.setBookId(cartItem.getBook().getId());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}
