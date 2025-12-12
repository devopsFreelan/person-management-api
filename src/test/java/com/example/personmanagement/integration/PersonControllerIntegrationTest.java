package com.example.personmanagement.integration;

import com.example.personmanagement.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndGetPerson() {
        String url = "http://localhost:" + port + "/api/persons";
        PersonDTO p = new PersonDTO(null, "Integration", "Test", "it@example.com", 99);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<PersonDTO> request = new HttpEntity<>(p, headers);
        ResponseEntity<PersonDTO> resp = restTemplate.postForEntity(url, request, PersonDTO.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getBody()).isNotNull();
        Long id = resp.getBody().getId();
        assertThat(id).isNotNull();

        // get the created person
        ResponseEntity<PersonDTO> get = restTemplate.getForEntity(url + "/" + id, PersonDTO.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get.getBody().getEmail()).isEqualTo("it@example.com");
    }
}
