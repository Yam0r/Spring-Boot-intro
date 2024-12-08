package mate.academy.springintro.dto.order;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springintro.model.OrderItem;

@AllArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    private String shippingAddress;
    private Set<OrderItem> orderItems;
}
