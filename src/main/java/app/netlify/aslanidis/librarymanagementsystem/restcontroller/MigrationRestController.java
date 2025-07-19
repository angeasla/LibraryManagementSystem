package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.service.utilities.DataMigrationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for data migration operations.
 * This should be used only during the transition to the new BookCopy model.
 */
@RestController
@RequestMapping("/api/migration")
public class MigrationRestController {

    private final DataMigrationUtility migrationUtility;

    @Autowired
    public MigrationRestController(DataMigrationUtility migrationUtility) {
        this.migrationUtility = migrationUtility;
    }

    /**
     * Creates default copies for books that don't have any copies yet.
     * This is the safer migration approach.
     */
    @PostMapping("/create-default-copies")
    public ResponseEntity<String> createDefaultCopies() {
        try {
            migrationUtility.createDefaultCopiesForBooksWithoutCopies();
            return new ResponseEntity<>("Default copies created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Migration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * WARNING: Use this only if you still have the quantity field in your database
     * and want to migrate based on those values.
     */
    @PostMapping("/migrate-quantities")
    public ResponseEntity<String> migrateQuantities() {
        try {
            migrationUtility.migrateBookQuantitiesToBookCopies();
            return new ResponseEntity<>("Quantity migration completed successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Migration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}