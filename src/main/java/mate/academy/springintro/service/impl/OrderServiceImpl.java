package mate.academy.springintro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.config.OrderMapper;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.OrderStatus;
import mate.academy.springintro.repository.OrderRepository;
import mate.academy.springintro.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order placeOrder(Long userId, String shippingAddress, Set<OrderItem> orderItems) {
        OrderRequestDto orderRequestDto = new OrderRequestDto(shippingAddress, orderItems);
        Order order = orderMapper.toOrder(userId, orderRequestDto, orderItems);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
