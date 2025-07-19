package app.netlify.aslanidis.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "BOOK_COPIES")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COPY_ID", nullable = false)
    private Long copyId;

    @ManyToOne
    @JsonIgnoreProperties("copies")
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    @Column(name = "COPY_NUMBER", nullable = false)
    private Integer copyNumber; // 1, 2, 3, etc. for the same book

    @Column(name = "IS_AVAILABLE", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "CONDITION")
    private String condition; // "NEW", "GOOD", "FAIR", "POOR"

    @OneToMany(mappedBy = "bookCopy", cascade = CascadeType.ALL)
    private List<Borrow> borrows;
}