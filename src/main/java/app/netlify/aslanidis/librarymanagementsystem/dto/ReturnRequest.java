package app.netlify.aslanidis.librarymanagementsystem.dto;

public class ReturnRequest {
    private Long userId;
    private Long bookId;

    public ReturnRequest() {
    }

    public ReturnRequest(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

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
