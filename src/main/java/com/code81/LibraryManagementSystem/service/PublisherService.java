package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.PublisherDTO;
import com.code81.LibraryManagementSystem.entity.Book;
import com.code81.LibraryManagementSystem.entity.Publisher;
import com.code81.LibraryManagementSystem.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherDTO getPublisherById(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        return mapTO(publisher);
    }

    public List<PublisherDTO> getAllPublisher() {
        return publisherRepository.findAll().stream()
                .map(this::mapTO).toList();
    }


    public PublisherDTO updatePublisher(Integer publisherId, PublisherDTO updatePublisher) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id " + publisherId));
        publisher.setName(updatePublisher.name());
        publisher.setAddress(updatePublisher.address());

        Publisher saved = publisherRepository.save(publisher);
        return mapTO(saved);
    }

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.name());
        publisher.setAddress(publisherDTO.address());

        Publisher saved = publisherRepository.save(publisher);

        return mapTO(saved);
    }

    public PublisherDTO mapTO(Publisher publisher) {
        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getAddress(),
                publisher.getBooks() == null ? null :
                        publisher.getBooks().stream().map(Book::getTitle).toList()

        );
    }

    @Transactional
    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        publisher.getBooks().forEach(book -> book.setPublisher(null));

        publisherRepository.delete(publisher);


    }
}
