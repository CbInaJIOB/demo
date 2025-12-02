package pro.sky.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.demo.controller.BookController;
import pro.sky.demo.model.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoWebApplicationTestsRestTemplate {


    @LocalServerPort
    private int port;

    @Autowired
    private BookController bookController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(bookController).isNotNull();
    }

    @Test
    public  void testDefaultMessage() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .isEqualTo("WebApp is working!");
    }

    @Test
    public  void testGetBooks() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/books", String.class))
                .isNotEmpty();
    }

    @Test
    public  void testPostBooks() throws Exception {     // фиговый метод, он добавляет книгу в базу данных
        Book book = new Book();
        book.setName("savas");
        book.setAuthor("FDfdbdfb");
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/books", book, String.class))
                .isNotEmpty();
    }
}
