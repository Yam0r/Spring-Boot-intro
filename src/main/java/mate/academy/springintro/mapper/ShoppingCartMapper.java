package mate.academy.springintro.mapper;

import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.shoppingcart.CartItemResponseDto;
import mate.academy.springintro.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart);

    @Mapping(target = "bookId", source = "book.id")
    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);
}

