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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCopy> copies;

    // Calculated field - total number of copies
    public Integer getTotalCopies() {
        return copies != null ? copies.size() : 0;
    }

    // Calculated field - available copies
    public Integer getAvailableCopies() {
        return copies != null ? 
            (int) copies.stream().filter(BookCopy::getIsAvailable).count() : 0;
    }
}
