package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springintro.dto.order.OrderRequestDto;
import mate.academy.springintro.dto.order.OrderResponseDto;
import mate.academy.springintro.dto.order.UpdateOrderStatusRequest;
import mate.academy.springintro.model.User;
import mate.academy.springintro.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Place a new order",
            description = "Place a new order with items and shipping address")
    public OrderResponseDto placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                       Authentication authentication) {
        return orderService.placeOrder((User) authentication.getPrincipal(), orderRequestDto);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Get order history",
            description = "Retrieve a list of all orders made by the user")
    public Page<OrderResponseDto> getOrderHistory(Authentication authentication,
                                                  Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderHistory(user.getId(), pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Update the status of an existing order by its ID")
    public OrderResponseDto updateOrderStatus(@PathVariable Long id,
                                              @RequestBody @Valid UpdateOrderStatusRequest
                                                      updateRequest) {
        return orderService.updateOrderStatus(id, updateRequest);
    }
}
