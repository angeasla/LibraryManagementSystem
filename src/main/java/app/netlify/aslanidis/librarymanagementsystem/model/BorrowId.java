package app.netlify.aslanidis.librarymanagementsystem.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
public class BorrowId implements Serializable {
    private Long userId;
    private Long bookId;
    private LocalDateTime borrowTimestamp;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setBorrowTimestamp(LocalDateTime borrowTimestamp) {
        this.borrowTimestamp = borrowTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowId borrowId = (BorrowId) o;
        return Objects.equals(userId, borrowId.userId) && Objects.equals(bookId, borrowId.bookId) && Objects.equals(borrowTimestamp, borrowId.borrowTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId, borrowTimestamp);
    }
}
