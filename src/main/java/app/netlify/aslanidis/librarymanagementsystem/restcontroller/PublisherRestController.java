package app.netlify.aslanidis.librarymanagementsystem.restcontroller;

import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import app.netlify.aslanidis.librarymanagementsystem.service.IPublisherService;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherRestController {

    private final IPublisherService publisherService;

    @Autowired
    public PublisherRestController(IPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("{publisherId}")
    public Publisher getPublisherById(@PathVariable("publisherId") Long publisherId) throws EntityNotFoundException {
        return publisherService.getPublisherById(publisherId);
    }

    @GetMapping("/search-by-name/{publisherName}")
    public ResponseEntity<Publisher> getPublisherByName(@PathVariable String publisherName) throws EntityNotFoundException {
        Publisher publisher = publisherService.getPublisherByName(publisherName);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @PutMapping("/{publisherId}")
    public Publisher updatePublisher(@PathVariable("publisherId") Long publisherId, @RequestBody Publisher publisher) throws EntityNotFoundException {
        return publisherService.updatePublisher(publisherId, publisher);
    }

    @DeleteMapping("/{publisherId}")
    public void deletePublisher(@PathVariable("publisherId") Long publisherId) throws EntityNotFoundException {
        publisherService.deletePublisher(publisherId);
    }
}
