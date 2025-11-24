package pro.sky.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.demo.model.Book;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameIgnoreCase(String name);

    Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);

    Collection<Book> findAllByNameContainsIgnoreCase(String part);

    Collection<Book> findBooksByNameIgnoreCaseAndAuthor(String name, String author);

    Collection<Book> findBooksByNameOrAuthor(String name, String author);
}
