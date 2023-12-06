package app.netlify.aslanidis.librarymanagementsystem.service;

import app.netlify.aslanidis.librarymanagementsystem.dto.BookDTO;
import app.netlify.aslanidis.librarymanagementsystem.dto.PublisherDTO;
import app.netlify.aslanidis.librarymanagementsystem.model.Book;
import app.netlify.aslanidis.librarymanagementsystem.model.Publisher;
import app.netlify.aslanidis.librarymanagementsystem.repository.PublisherRepository;
import app.netlify.aslanidis.librarymanagementsystem.service.exceptions.EntityNotFoundException;
import app.netlify.aslanidis.librarymanagementsystem.service.utilities.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements IPublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Page<PublisherDTO> getAllPublishersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Publisher> publisherPage = publisherRepository.findAllByOrderByNameAsc(pageable);

        List<PublisherDTO> publishersDto = publisherPage.stream()
                .map(DTOConverter::convertPublisherToShortDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(publishersDto, pageable, publisherPage.getTotalElements());
    }

    @Override
    public Long countPublishers() {
        return publisherRepository.count();
    }

    @Override
    public Publisher getPublisherById(Long publisherId) throws EntityNotFoundException {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
    }

    @Override
    public Publisher getPublisherByName(String publisherName) {
        return publisherRepository.findPublisherByNameContainingIgnoreCase(publisherName);
    }

    @Transactional
    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Transactional
    @Override
    public Publisher updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException {
        if (!publisherRepository.existsById(publisherId)) {
            throw new EntityNotFoundException("Publisher not found");
        }
        publisher.setPublisherId(publisherId);
        return publisherRepository.save(publisher);
    }

    @Transactional
    @Override
    public void deletePublisher(Long publisherId) {
        publisherRepository.deleteById(publisherId);
    }


}
