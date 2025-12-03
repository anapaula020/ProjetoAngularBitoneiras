package br.unitins.tp1.dto.mercadopago;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayerDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @Valid
    private IdentificationDTO identification;

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public IdentificationDTO getIdentification() { return identification; }
    public void setIdentification(IdentificationDTO identification) { this.identification = identification; }
}
