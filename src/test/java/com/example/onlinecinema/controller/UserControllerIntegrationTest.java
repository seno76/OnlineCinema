package com.example.onlinecinema.integration;

import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser = userRepository.save(testUser);
    }

    // GET /users/{id}

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() {
        // Act
        ResponseEntity<User> response = restTemplate.getForEntity(
                "/users/" + testUser.getUserId(),
                User.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void getUserById_ShouldReturn404_WhenUserNotExists() {
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/users/999999",
                String.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Пользователь с ID 999999 не найден");
    }

    // GET /users/{id}

    @Test
    public void createUser_ShouldReturn201_WhenDataIsValid() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpass");
        newUser.setEmail("new@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);

        // Act
        ResponseEntity<User> response = restTemplate.postForEntity(
                "/users",
                request,
                User.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("newuser");
        assertThat(userRepository.findByUsername("newuser")).isPresent();
    }

    // DELETE /users/{id}

    @Test
    public void deleteUser_ShouldReturn204_WhenUserExists() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "adminpass"); // предполагаем, что есть админ

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                "/users/" + testUser.getUserId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(userRepository.findById(testUser.getUserId())).isEmpty();
    }

    @Test
    public void deleteUser_ShouldReturn403_WhenNotAdmin() {
        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                "/users/" + testUser.getUserId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}