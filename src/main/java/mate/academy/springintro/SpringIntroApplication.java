package mate.academy.springintro;

import java.math.BigDecimal;
import mate.academy.springintro.model.CreatedBookDto;
import mate.academy.springintro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIntroApplication implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntroApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CreatedBookDto book = new CreatedBookDto();
        book.setTitle("Grooks algorithms");
        book.setAuthor("Bob Johnson");
        book.setIsbn("978-4321567890");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setDescription("A comprehensive guide to the Spring Framework.");
        book.setCoverImage("image_url_here");

        bookService.save(book);

        System.out.println("Book saved successfully!");
    }
}
