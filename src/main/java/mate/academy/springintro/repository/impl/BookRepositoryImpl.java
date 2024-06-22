package mate.academy.springintro.repository.impl;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import mate.academy.springintro.repository.BookRepository;

public class BookRepositoryImpl implements BookRepository {
    private List<Book> books = new ArrayList<>();

    @Override
    public Book save(Book book) {
        books.add(book);
        return book;

    }

    @Override
    public List findAll() {
        return new ArrayList<>(books);
    }
}
