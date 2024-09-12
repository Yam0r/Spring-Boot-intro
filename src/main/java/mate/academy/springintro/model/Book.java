package mate.academy.springintro.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE bookshop SET is_deleted = true WHERE id=?")
@Table(name = "bookshop")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;
    private String coverImage;

    @Column(nullable = false)
    private boolean isDeleted;
}
