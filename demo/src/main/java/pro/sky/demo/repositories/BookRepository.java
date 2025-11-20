package pro.sky.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
