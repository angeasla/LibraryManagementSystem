package app.netlify.aslanidis.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", nullable = false)
    private Long bookId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "PAGES")
    private Long pages;

    @Column(name = "STATUS")
    private Integer status;
}
