package app.netlify.aslanidis.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID", nullable = false)
    private Long authorId;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Book> books;
}
