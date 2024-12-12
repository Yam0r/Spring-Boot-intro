package mate.academy.springintro.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.springintro.model.OrderStatus;

public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        double total,
        OrderStatus status
) {
}
