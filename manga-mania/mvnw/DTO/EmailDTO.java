package mssaat.org.DTO;

import jakarta.validation.constraints.Size;

public record EmailDTO(
    @Size(min = 4, max = 30) String email) {
}
