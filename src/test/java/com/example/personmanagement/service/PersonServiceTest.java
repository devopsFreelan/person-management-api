package com.example.personmanagement.service;

import com.example.personmanagement.dto.PersonDTO;
import com.example.personmanagement.exception.ResourceNotFoundException;
import com.example.personmanagement.model.Person;
import com.example.personmanagement.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    private PersonRepository repo;
    private PersonService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(PersonRepository.class);
        service = new PersonService(repo);
    }

    @Test
    void shouldFindAll() {
        when(repo.findAll()).thenReturn(List.of(new Person(1L, "John", "Doe", "john@example.com", 30)));
        List<PersonDTO> list = service.findAll();
        assertEquals(1, list.size());
        assertEquals("John", list.get(0).getFirstName());
    }

    @Test
    void shouldFindById() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Person(1L, "John", "Doe", "john@example.com", 30)));
        PersonDTO dto = service.findById(1L);
        assertEquals("John", dto.getFirstName());
    }

    @Test
    void findByIdNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldCreate() {
        Person pers = new Person(null, "Jane", "Doe", "jane@example.com", 25);
        Person saved = new Person(2L, "Jane", "Doe", "jane@example.com", 25);
        when(repo.save(ArgumentMatchers.any(Person.class))).thenReturn(saved);
        PersonDTO created = service.create(PersonDTO.fromEntity(pers));
        assertNotNull(created.getId());
        assertEquals("jane@example.com", created.getEmail());
    }

    @Test
    void shouldUpdate() {
        Person existing = new Person(1L, "John", "Doe", "john@example.com", 30);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(ArgumentMatchers.any(Person.class))).thenAnswer(i -> i.getArgument(0));
        PersonDTO update = new PersonDTO(null, "John", "Smith", "john.smith@example.com", 31);
        PersonDTO updated = service.update(1L, update);
        assertEquals("Smith", updated.getLastName());
        assertEquals(31, updated.getAge().intValue());
    }

    @Test
    void updateNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, new PersonDTO()));
    }

    @Test
    void shouldDelete() {
        Person existing = new Person(1L, "John", "Doe", "john@example.com", 30);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(repo).delete(existing);
        service.delete(1L);
        verify(repo, times(1)).delete(existing);
    }

    @Test
    void deleteNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }
}
