package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import app.netlify.aslanidis.librarymanagementsystem.repository.PublisherRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements IPublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher getPublisherById(Long publisherId) throws EntityNotFoundException {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
    }

    @Override
    public Publisher getPublisherByName(String publisherName) {
        return publisherRepository.findPublisherByName(publisherName);
    }

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException {
        if (!publisherRepository.existsById(publisherId)) {
            throw new EntityNotFoundException("Publisher not found");
        }
        publisher.setPublisherId(publisherId);
        return publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(Long publisherId) {
        publisherRepository.deleteById(publisherId);
    }
}
