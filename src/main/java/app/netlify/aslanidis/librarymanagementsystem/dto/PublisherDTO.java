package app.netlify.aslanidis.librarymanagementsystem.dto;

public class PublisherDTO {
    private Long publisherId;
    private String name;

    public PublisherDTO() {
    }

    public PublisherDTO(Long publisherId, String name) {
        this.publisherId = publisherId;
        this.name = name;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
