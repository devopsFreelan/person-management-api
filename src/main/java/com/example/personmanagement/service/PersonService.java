package com.example.personmanagement.service;

import com.example.personmanagement.dto.PersonDTO;
import com.example.personmanagement.exception.ResourceNotFoundException;
import com.example.personmanagement.model.Person;
import com.example.personmanagement.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonDTO> findAll() {
        return repository.findAll().stream().map(PersonDTO::fromEntity).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) {
        Person p = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        return PersonDTO.fromEntity(p);
    }

    public PersonDTO create(PersonDTO dto) {
        Person saved = repository.save(PersonDTO.toEntity(dto));
        return PersonDTO.fromEntity(saved);
    }

    public PersonDTO update(Long id, PersonDTO dto) {
        Person existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        Person updated = repository.save(existing);
        return PersonDTO.fromEntity(updated);
    }

    public void delete(Long id) {
        Person existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        repository.delete(existing);
    }
}
