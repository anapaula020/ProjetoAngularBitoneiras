package br.unitins.tp1.exception;

import jakarta.ws.rs.core.Response;

public class ServiceException extends RuntimeException {

    private final String code;
    private final int statusCode;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.statusCode = parseStatusCode(code);
    }

    public ServiceException(String message, Response.Status status) {
        super(message);
        this.code = String.valueOf(status.getStatusCode());
        this.statusCode = status.getStatusCode();
    }

    public ServiceException(String message) {
        super(message);
        this.code = "500";
        this.statusCode = 500;
    }

    private int parseStatusCode(String code) {
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return 500;
        }
    }

    public String getCode() {
        return code;
    }

    public int getStatusCode() {
        return statusCode;
    }
}