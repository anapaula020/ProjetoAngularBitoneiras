package br.unitins.tp1.exception;

import jakarta.ws.rs.core.Response;

public class ServiceException extends RuntimeException {

    private final String code; // Alterado para String para flexibilidade
    private final int statusCode; // Mantido para compatibilidade com Response.Status

    // Construtor principal que aceita um código de erro String e uma mensagem
    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.statusCode = parseStatusCode(code); // Tenta converter o código para int
    }

    // Construtor para usar com Response.Status
    public ServiceException(String message, Response.Status status) {
        super(message);
        this.code = String.valueOf(status.getStatusCode());
        this.statusCode = status.getStatusCode();
    }

    // Construtor para erros genéricos 500
    public ServiceException(String message) {
        super(message);
        this.code = "500";
        this.statusCode = 500;
    }

    // Método auxiliar para converter String code para int statusCode
    private int parseStatusCode(String code) {
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return 500; // Default para erro interno se o código não for um número
        }
    }

    public String getCode() {
        return code;
    }

    public int getStatusCode() {
        return statusCode;
    }
}