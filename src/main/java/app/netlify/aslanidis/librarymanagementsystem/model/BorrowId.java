package app.netlify.aslanidis.librarymanagementsystem.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BorrowId implements Serializable {
    private Long userId;
    private Long bookId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
