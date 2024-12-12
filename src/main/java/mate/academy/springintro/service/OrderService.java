package mate.academy.springintro.service;

import mate.academy.springintro.dto.order.OrderItemResponseDto;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.dto.order.OrderResponseDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequest;
import mate.academy.springintro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto placeOrder(User user, OrderRequestDto requestDto);

    Page<OrderResponseDto> getOrderHistory(Long id, Pageable pageable);

    Page<OrderItemResponseDto> getOrderItems(Long orderId, Pageable pageable);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);

    OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequest updateRequest);
}
