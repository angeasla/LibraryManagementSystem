package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IPublisherService {
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(Long publisherId) throws EntityNotFoundException;
    Publisher getPublisherByName(String publisherName);
    Publisher createPublisher(Publisher publisher);
    Publisher updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException;
    void deletePublisher(Long publisherId);
}
