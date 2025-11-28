package pro.sky.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.demo.model.Book;
import pro.sky.demo.model.BookCover;
import pro.sky.demo.repositories.BookCoverRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class BookCoverService {

    @Value("covers")
    private String coversDir;

    private final BookService bookService;
    private final BookCoverRepository bookCoverRepository;

    public BookCoverService(BookService bookService, BookCoverRepository bookCoverRepository) {
        this.bookService = bookService;
        this.bookCoverRepository = bookCoverRepository;
    }

    public void uploadCover(Long bookId, MultipartFile file) throws IOException {
        Book book = bookService.findBook(bookId);       // получаем книгу по id

        Path filePath = Path.of(coversDir, bookId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());      // проверяет есть ли папки , если нехватает папок - создает
        Files.deleteIfExists(filePath);                     // удаляет файл, если он существует


        // try для закрытия потока(автоматически закрывает поток)
        try (InputStream is = file.getInputStream();        // создаем входной поток данных
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);     // создает пустой файл в который записывается поток
             BufferedInputStream bis = new BufferedInputStream(is, 1024);   // создается буффер для входного потока
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);        // передача данных из входного потока в выходной
        }

        BookCover bookCover = findBookCover(bookId);
        bookCover.setBook(book);
        bookCover.setFilePath(filePath.toString());
        bookCover.setFileSize(file.getSize());
        bookCover.setMediaType(file.getContentType());
        bookCover.setPreview(generateImagePreview(filePath));

        bookCoverRepository.save(bookCover);
    }

    public BookCover findBookCover(Long bookId) {
        return bookCoverRepository.findByBookId(bookId).orElse(new BookCover());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getHeight() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();

        }
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
