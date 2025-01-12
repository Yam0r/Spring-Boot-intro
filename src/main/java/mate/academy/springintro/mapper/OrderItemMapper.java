package mate.academy.springintro.mapper;

import java.util.Set;
import mate.academy.springintro.config.MapperConfig;
import mate.academy.springintro.dto.order.OrderItemResponseDto;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    Set<OrderItem> toEntity(Set<CartItem> cartItems);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem toEntity(CartItem cartItem);
}
