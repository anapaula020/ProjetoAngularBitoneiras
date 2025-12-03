package br.unitins.tp1.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapeador de exceções para ServiceException.
 * Intercepta ServiceException lançadas pelos serviços e as transforma em respostas HTTP adequadas.
 */
@Provider // Registra esta classe como um ExceptionMapper no JAX-RS (Quarkus/Spring)
public class ServiceExceptionHandler implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception) {
        // Opcional: Logar a exceção para fins de depuração
        // System.err.println("Service Exception capturada: " + exception.getMessage());
        // exception.printStackTrace(); // Para logs mais detalhados

        // Cria uma resposta HTTP usando o código de status e a mensagem da ServiceException
        return Response.status(exception.getStatusCode()) // Chama getStatusCode()
                       .entity(exception.getMessage())    // Define a mensagem no corpo da resposta
                       .build();                           // Constrói a resposta
    }
}