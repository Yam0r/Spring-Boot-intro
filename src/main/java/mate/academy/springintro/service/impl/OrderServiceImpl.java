package mate.academy.springintro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.order.OrderItemResponseDto;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.dto.order.OrderResponseDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequest;
import mate.academy.springintro.mapper.OrderItemMapper;
import mate.academy.springintro.mapper.OrderMapper;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.OrderStatus;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.OrderItemRepository;
import mate.academy.springintro.repository.OrderRepository;
import mate.academy.springintro.repository.ShoppingCartRepository;
import mate.academy.springintro.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderResponseDto placeOrder(User user, OrderRequestDto requestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());

        Set<CartItem> cartContent =
                shoppingCartRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Shopping cart not found for user ID: "
                                                + user.getId()))
                        .getCartItems();

        if (cartContent.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot place an order with an empty shopping cart for user ID: + "
                            + user.getId());
        }

        Set<OrderItem> orderContent = orderItemMapper.toEntity(cartContent);
        orderContent.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderContent);
        order.setTotal(calculateTotal(orderContent));
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderResponseDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderRepository
                .findByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public Page<OrderItemResponseDto> getOrderItems(Long orderId, Pageable pageable) {
        return orderItemRepository
                .findByOrderId(orderId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        return orderItemMapper.toDto(orderItemRepository.findByOrderIdAndId(orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrderItem not found for order ID: " + orderId + " item ID: " + itemId)));
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequest updateRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found for ID: " + id));
        orderMapper.update(order, updateRequest);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderContent) {
        return orderContent.stream()
                .map(OrderItem::getOrderItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
