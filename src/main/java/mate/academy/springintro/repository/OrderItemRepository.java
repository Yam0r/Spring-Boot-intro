package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findByOrderId(Long orderId, Pageable pageable);

    Optional<OrderItem> findByOrderIdAndId(Long orderId, Long id);
}
