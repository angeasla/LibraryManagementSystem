package app.netlify.aslanidis.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ManyToOne
    @JsonIgnoreProperties("books")
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @Column(name = "ISBN")
    private String isbn;

    @ManyToOne
    @JsonIgnoreProperties("books")
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    private Publisher publisher;

    @Column(name = "PAGES")
    private Long pages;

    @Column(name = "PUBLICATION_YEAR")
    private Long publicationYear;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrow> borrows; // One book can be borrowed multiple times

    public Integer addBook() {
        return quantity++;
    }

    public Integer removeBook() {
        return quantity--;
    }
}
