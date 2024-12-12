package mate.academy.springintro.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.springintro.model.OrderStatus;

public record UpdateOrderStatusRequest(@NotNull OrderStatus status) {
}
