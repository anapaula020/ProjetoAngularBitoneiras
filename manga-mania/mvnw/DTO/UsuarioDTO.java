package mssaat.org.DTO;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mssaat.org.model.Endereco;

public record UsuarioDTO(
        @NotBlank(message = "Nome não pode ficar vazio.") @Size(min = 4, max = 80, message = "Nome está grande demais.") String username,
        @NotBlank(message = "Email não pode ficar vazio.") @Size(min = 6, message = "Email está pequeno demais.") @Size(max = 60, message = "Email está grande demais.") @Email String email,
        @NotBlank(message = "Senha não pode ficar vazia.") @Size(min = 6, message = "Senha está pequena demais.") @Size(max = 60, message = "Senha está grande demais.") String senha,
        @NotBlank(message = "CPF não pode ficar vazio.") @Size(min = 10, message = "CPF está pequeno demais.") @Size(max = 12, message = "CPF está grande demais.") String cpf,
        EnderecoDTO endereco,
        Integer sexo) {
}