package app.netlify.aslanidis.librarymanagementsystem.service.utilities;

import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.BookCopy;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookCopyRepository;
import app.netlify.aslanidis.librarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Utility class to help migrate data from the old Book model (with quantity field)
 * to the new BookCopy model.
 * 
 * This should be run once after deploying the new model structure.
 */
@Component
public class DataMigrationUtility {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public DataMigrationUtility(BookRepository bookRepository, BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    /**
     * Migrates existing books to create BookCopy entries based on their quantity.
     * This method should be called once after the database schema is updated.
     * 
     * WARNING: This assumes that the old 'quantity' column still exists in the database.
     * You may need to run this before removing the quantity column from the database.
     */
    @Transactional
    public void migrateBookQuantitiesToBookCopies() {
        List<Book> allBooks = bookRepository.findAll();
        
        for (Book book : allBooks) {
            // Check if this book already has copies
            List<BookCopy> existingCopies = bookCopyRepository.findByBook(book);
            
            if (existingCopies.isEmpty()) {
                // Create copies based on the old quantity field
                // Note: You'll need to modify this if the quantity field is already removed
                Integer quantity = getBookQuantityFromDatabase(book); // You'll need to implement this
                
                if (quantity != null && quantity > 0) {
                    for (int i = 1; i <= quantity; i++) {
                        BookCopy copy = new BookCopy();
                        copy.setBook(book);
                        copy.setCopyNumber(i);
                        copy.setIsAvailable(true);
                        copy.setCondition("GOOD"); // Default condition
                        bookCopyRepository.save(copy);
                    }
                    System.out.println("Created " + quantity + " copies for book: " + book.getTitle());
                }
            }
        }
        
        System.out.println("Migration completed successfully!");
    }

    /**
     * This method would need to be implemented to get the quantity from the database
     * if the quantity field still exists. If it's already removed, you'll need to
     * handle this differently (e.g., set a default quantity or ask the user).
     */
    private Integer getBookQuantityFromDatabase(Book book) {
        // TODO: Implement this method to get quantity from database
        // This might require a native SQL query if the quantity field still exists
        // For now, return a default value
        return 1; // Default to 1 copy per book
    }

    /**
     * Creates a single copy for books that don't have any copies yet.
     * This is a safer migration approach if you're unsure about quantities.
     */
    @Transactional
    public void createDefaultCopiesForBooksWithoutCopies() {
        List<Book> allBooks = bookRepository.findAll();
        
        for (Book book : allBooks) {
            List<BookCopy> existingCopies = bookCopyRepository.findByBook(book);
            
            if (existingCopies.isEmpty()) {
                BookCopy copy = new BookCopy();
                copy.setBook(book);
                copy.setCopyNumber(1);
                copy.setIsAvailable(true);
                copy.setCondition("GOOD");
                bookCopyRepository.save(copy);
                
                System.out.println("Created default copy for book: " + book.getTitle());
            }
        }
        
        System.out.println("Default copies creation completed!");
    }
}