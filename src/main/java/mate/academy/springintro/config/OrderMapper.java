package mate.academy.springintro.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.OrderStatus;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.UserService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderMapper {

    private final UserService userService;

    public Order toOrder(Long userId, OrderRequestDto orderRequestDto, Set<OrderItem> orderItems) {
        User user = userService.findById(userId);
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(calculateTotal(orderItems));
        order.setOrderDate(LocalDateTime.now());
        orderItems.forEach(item -> item.setOrder(order));
        return order;
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
