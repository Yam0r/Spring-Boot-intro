package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.model.Order;
import mate.academy.springintro.model.OrderItem;
import mate.academy.springintro.model.OrderStatus;
import mate.academy.springintro.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Place a new order",
            description = "Place a new order with items and shipping address")
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        Set<OrderItem> orderItems = getOrderItemsFromRequest(orderRequestDto);
        Order order = orderService.placeOrder(userId,
                orderRequestDto.getShippingAddress(), orderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Get order history",
            description = "Retrieve a list of all orders made by the user")
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication,
                                                       Pageable pageable) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Order> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Update the status of an existing order by its ID")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestBody @Valid OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        return Long.valueOf(authentication.getName());
    }

    private Set<OrderItem> getOrderItemsFromRequest(OrderRequestDto orderRequestDto) {
        return orderRequestDto.getOrderItems();
    }
}
