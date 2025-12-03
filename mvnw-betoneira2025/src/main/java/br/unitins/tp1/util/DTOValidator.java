package br.unitins.tp1.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class DTOValidator {

    @Inject
    Validator validator;

    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String messages = violations.stream()
                .map(v -> String.format("[%s]: %s", v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.joining("; "));

            throw new ConstraintViolationException("Erro de validação: " + messages, violations);
        }
    }
}