package pro.sky.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.demo.model.Book;
import pro.sky.demo.service.BookService;

import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}")         //GET получить книгу по id
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

//    @GetMapping                 //GET   получить все книги
//    public ResponseEntity<Collection<Book>> getAllBooks() {
//        return ResponseEntity.ok(bookService.getAllBooks());
//    }

    @GetMapping
    public ResponseEntity findBooks(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String namePart) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(bookService.findByName(name));
        }
        if (author != null && !author.isBlank()) {
            return ResponseEntity.ok(bookService.findByAuthor(author));
        }
        if (namePart != null && !namePart.isBlank()) {
            return ResponseEntity.ok(bookService.findByNamePart(namePart));
        }
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping()              //POST  создать книгу
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping()               //PUT   редактировать книгу
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundBook = bookService.editBook(book);
        if (foundBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundBook);
    }

    @DeleteMapping("{id}")      //DELETE    удалить книгу
    public ResponseEntity deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}