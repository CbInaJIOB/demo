package pro.sky.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.demo.model.BookCover;

import java.util.Optional;

public interface BookCoverRepository  extends JpaRepository<BookCover, Long> {

    Optional<BookCover> findByBookId(Long bookId);
}
