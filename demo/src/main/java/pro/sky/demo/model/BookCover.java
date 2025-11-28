package pro.sky.demo.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class BookCover {

    @Id
    @GeneratedValue
    private long id;

    private String filePath;    // путь файла на диске
    private long fileSize;      // размер файла

    private String mediaType;

    @Lob
    private byte[] preview;     //превью обложка уменьшенная в размере, хранится в базе данных

    @OneToOne
    private Book book;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookCover{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", preview=" + Arrays.toString(preview) +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCover bookCover = (BookCover) o;
        return id == bookCover.id && fileSize == bookCover.fileSize && Objects.equals(filePath, bookCover.filePath) && Objects.equals(mediaType, bookCover.mediaType) && Objects.deepEquals(preview, bookCover.preview) && Objects.equals(book, bookCover.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, mediaType, Arrays.hashCode(preview), book);
    }
}
