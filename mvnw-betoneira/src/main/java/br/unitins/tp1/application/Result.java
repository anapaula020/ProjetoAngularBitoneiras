package br.unitins.tp1.application;

import java.util.Map;

public class Result {
    private boolean success;
    private Object data;
    private String message;
    private Map<String, String> errors;

    private Result(boolean success, Object data, String message, Map<String, String> errors) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errors = errors;
    }

    public static Result ok(Object data) {
        return new Result(true, data, null, null);
    }

    public static Result ok() {
        return new Result(true, null, null, null);
    }

    public static Result valueOf(String message) {
        return new Result(false, null, message, null);
    }

    public static Result validationErrors(Map<String, String> errors) {
        return new Result(false, null, "Erros de validação", errors);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}