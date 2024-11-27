package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.shoppingcart.AddToCartRequestDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.dto.shoppingcart.UpdateCartItemRequestDto;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart Management",
        description = "Operations related to managing the user's shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(
            summary = "Get the shopping cart for the authenticated user",
            description = "Fetch the shopping cart details, including items and quantities, "
                    + "for the authenticated user."
    )
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ShoppingCartResponseDto getCartForUser(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return shoppingCartService.getCartForUser(userId);
    }

    @Operation(
            summary = "Add an item to the shopping cart",
            description = "Add a specified book and quantity to "
                    + "the authenticated user's shopping cart."
    )
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ShoppingCartResponseDto addToCart(Authentication authentication,
                                             @RequestBody @Valid AddToCartRequestDto requestDto) {
        Long userId = getUserIdFromAuth(authentication);
        return shoppingCartService.addToCart(userId, requestDto);
    }

    @Operation(
            summary = "Update an item in the shopping cart",
            description = "Update the quantity of a specific item in "
                    + "the authenticated user's shopping cart."
    )
    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    public ShoppingCartResponseDto updateCartItem(Authentication authentication,
                                                  @PathVariable Long cartItemId,
                                                  @RequestBody @Valid UpdateCartItemRequestDto
                                                              requestDto) {
        Long userId = getUserIdFromAuth(authentication);
        return shoppingCartService.updateCartItem(userId, cartItemId, requestDto);
    }

    @Operation(
            summary = "Remove an item from the shopping cart",
            description = "Delete a specific item from the authenticated user's shopping cart."
    )
    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(Authentication authentication,
                               @PathVariable Long cartItemId) {
        Long userId = getUserIdFromAuth(authentication);
        shoppingCartService.removeCartItem(userId, cartItemId);
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }
}
