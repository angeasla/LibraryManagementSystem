package app.netlify.aslanidis.librarymanagementsystem.service.exceptions;

public class EntityNotAvailableException extends IllegalStateException {
    public EntityNotAvailableException(String message) {
        super(message);
    }
}
