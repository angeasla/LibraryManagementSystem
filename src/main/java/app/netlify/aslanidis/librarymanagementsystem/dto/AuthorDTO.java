package app.netlify.aslanidis.librarymanagementsystem.dto;

public class AuthorDTO {
    private Long authorId;
    private String firstname;
    private String lastname;

    public AuthorDTO() {
    }

    public AuthorDTO(Long authorId, String firstname, String lastname) {
        this.authorId = authorId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}