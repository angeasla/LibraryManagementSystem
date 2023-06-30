package app.netlify.aslanidis.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "BORROWS")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BORROW_ID", nullable = false)
    private Long borrowId;

    @Column(name = "BORROW_DATE", nullable = false)
    private Date borrowDate;

    @Column(name = "RETURN_DATE", nullable = true)
    private Date returnDate;

    @Column(name = "RETURNED")
    private Integer returned;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL)
    private List<BorrowedBook> borrowedBooks;
}
