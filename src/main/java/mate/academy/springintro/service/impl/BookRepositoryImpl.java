package mate.academy.springintro.service.impl;

import mate.academy.springintro.model.Book;
import mate.academy.springintro.service.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BookRepositoryImpl implements BookRepository {
    private List<Book> books = new ArrayList<>();

    @Override
    public Book save(Book book) {
        books.add(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }
}

