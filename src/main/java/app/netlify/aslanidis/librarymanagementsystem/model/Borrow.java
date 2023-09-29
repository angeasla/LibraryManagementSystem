package app.netlify.aslanidis.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "BORROWS")
public class Borrow {

    @EmbeddedId
    private BorrowId id;

    @Column(name = "BORROW_DATE", nullable = false)
    private Date borrowDate;

    @Column(name = "RETURN_DATE", nullable = true)
    private Date returnDate;

    @Column(name = "RETURNED")
    private Integer returned;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // This maps the userId attribute of embedded id
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")  // This maps the bookId attribute of embedded id
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
