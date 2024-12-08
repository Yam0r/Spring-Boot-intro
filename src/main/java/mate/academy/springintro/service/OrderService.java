package mate.academy.springintro.service;

import java.util.List;
import java.util.Set;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Order placeOrder(Long userId, String shippingAddress, Set<OrderItem> orderItems);

    List<Order> getUserOrders(Long userId, Pageable pageable);

    Order updateOrderStatus(Long orderId, OrderStatus status);
}
