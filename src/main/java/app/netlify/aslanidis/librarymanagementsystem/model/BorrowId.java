package app.netlify.aslanidis.librarymanagementsystem.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
}
