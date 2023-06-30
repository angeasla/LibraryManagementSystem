package app.netlify.aslanidis.librarymanagementsystem.dto;

import java.util.List;

public class BorrowRequest {
    private Long user;
    private List<Long> books;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }
}
