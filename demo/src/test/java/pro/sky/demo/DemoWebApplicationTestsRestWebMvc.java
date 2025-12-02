package pro.sky.demo;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pro.sky.demo.controller.BookController;
import pro.sky.demo.model.Book;
import pro.sky.demo.repositories.BookCoverRepository;
import pro.sky.demo.repositories.BookRepository;
import pro.sky.demo.service.BookCoverService;
import pro.sky.demo.service.BookService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest
public class DemoWebApplicationTestsRestWebMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookCoverRepository bookCoverRepository;

    @SpyBean
    private BookService bookService;

    @SpyBean
    private BookCoverService bookCoverService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void saveBookTest() throws Exception {
        final String name = "12312412";
        final String author = "sfsdfgsdf";
        final long id = 1;

        JSONObject bookObject = new JSONObject();
        bookObject.put("name", name);
        bookObject.put("author", author);

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/books")
                        .content(bookObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value (id))
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.author").value(author));
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(author));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(author));


    }


}
