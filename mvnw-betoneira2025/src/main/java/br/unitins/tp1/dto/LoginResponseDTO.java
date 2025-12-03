// src/main/java/br/unitins/tp1/dto/LoginResponseDTO.java
package br.unitins.tp1.dto;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private String token;

    public LoginResponseDTO(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    // Setters (optional, but good practice for DTOs if they might be mutated)
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}