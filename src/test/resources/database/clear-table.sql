DELETE FROM books_categories WHERE book_id IN (SELECT id FROM books);

DELETE FROM books;

DELETE FROM categories;
