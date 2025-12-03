package br.unitins.tp1.dto;

public class ErrorResponseDTO {
    private String code;    // Adicionado campo 'code'
    private String message;

    public ErrorResponseDTO(String code, String message) { // Construtor atualizado
        this.code = code;
        this.message = message;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    // Setters (se necessário, mas para DTO de resposta podem ser omitidos)
    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}