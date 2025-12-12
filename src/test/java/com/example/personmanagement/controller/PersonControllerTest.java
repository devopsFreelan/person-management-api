package com.example.personmanagement.controller;

import com.example.personmanagement.dto.PersonDTO;
import com.example.personmanagement.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldList() throws Exception {
        when(service.findAll()).thenReturn(List.of(new PersonDTO(1L, "John", "Doe", "john@example.com", 30)));
        mvc.perform(get("/api/persons").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void shouldCreate() throws Exception {
        PersonDTO input = new PersonDTO(null, "Jane", "Doe", "jane@example.com", 25);
        PersonDTO out = new PersonDTO(2L, "Jane", "Doe", "jane@example.com", 25);
        when(service.create(any())).thenReturn(out);
        mvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/persons/2"))
                .andExpect(jsonPath("$.id").value(2));
    }
}
