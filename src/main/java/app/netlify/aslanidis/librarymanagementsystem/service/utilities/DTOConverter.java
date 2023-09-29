package app.netlify.aslanidis.librarymanagementsystem.service.utilities;

import app.netlify.aslanidis.librarymanagementsystem.dto.AuthorDTO;
import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.dto.PublisherDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Author;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;

public class DTOConverter {

    public static BookDTO convertBookToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPages(book.getPages());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setQuantity(book.getQuantity());
        dto.setAuthor(convertAuthorToShortDTO(book.getAuthor()));
        dto.setPublisher(convertPublisherToShortDTO(book.getPublisher()));
        return dto;
    }

    public static AuthorDTO convertAuthorToShortDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(author.getAuthorId());
        dto.setFirstname(author.getFirstname());
        dto.setLastname(author.getLastname());
        return dto;
    }

    public static PublisherDTO convertPublisherToShortDTO(Publisher publisher) {
        PublisherDTO dto = new PublisherDTO();
        dto.setPublisherId(publisher.getPublisherId());
        dto.setName(publisher.getName());
        return dto;
    }

    public static Book convertDTOToBook(BookDTO dto) {
        Book book = new Book();
        book.setBookId(dto.getBookId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPages(dto.getPages());
        book.setPublicationYear(dto.getPublicationYear());
        book.setQuantity(dto.getQuantity());

        Author author = new Author();
        author.setAuthorId(dto.getAuthor().getAuthorId());
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(dto.getPublisher().getPublisherId());
        book.setPublisher(publisher);

        return book;
    }
}
