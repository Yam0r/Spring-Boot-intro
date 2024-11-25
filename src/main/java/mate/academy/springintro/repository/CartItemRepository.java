package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.Book;
import mate.academy.springintro.model.CartItem;
import mate.academy.springintro.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);
}
