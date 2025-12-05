package br.unitins.tp1.resource;

import br.unitins.tp1.dto.LoginRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testLoginUserSuccess() {
        LoginRequestDTO authRequest = new LoginRequestDTO("joao@example.com", "userpassword");
        given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("email", is("joao@example.com"));
    }

    @Test
    public void testLoginAdminSuccess() {
        LoginRequestDTO authRequest = new LoginRequestDTO("admin@example.com", "adminpassword");
        given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("email", is("admin@example.com"));
    }

    @Test
    public void testLoginUserInvalidPassword() {
        LoginRequestDTO authRequest = new LoginRequestDTO("joao@example.com", "wrongpassword");
        given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when().post("/auth")
                .then()
                .statusCode(401)
                .body(is("Email ou senha inválidos."));
    }

    @Test
    public void testLoginUserNotFound() {
        LoginRequestDTO authRequest = new LoginRequestDTO("nonexistent@example.com", "password");
        given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when().post("/auth")
                .then()
                .statusCode(401)
                .body(is("Email ou senha inválidos."));
    }

    
    
    public static String getAuthToken(String email, String password) {
        LoginRequestDTO authRequest = new LoginRequestDTO(email, password);
        int maxRetries = 5;
        int retryDelayMs = 1000; 

        for (int i = 0; i < maxRetries; i++) {
            try {
                return given()
                        .contentType(ContentType.JSON)
                        .body(authRequest)
                        .when().post("/auth")
                        .then()
                        .log().all() 
                        .statusCode(200) 
                        .extract().path("token");
            } catch (AssertionError e) {
                if (e.getMessage().contains("status code <401>") || e.getMessage().contains("status code <500>")) {
                    System.err.println("Attempt " + (i + 1) + "/" + maxRetries + ": Failed to get token (status " + e.getMessage().replaceAll("(?s).*status code <(\\d+)>.*", "$1") + "), retrying in " + retryDelayMs + "ms...");
                    try {
                        Thread.sleep(retryDelayMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted during token retry", ie);
                    }
                } else {
                    
                    throw e;
                }
            } catch (Exception e) { 
                System.err.println("Attempt " + (i + 1) + "/" + maxRetries + ": Failed to get token due to unexpected error: " + e.getMessage() + ", retrying in " + retryDelayMs + "ms...");
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted during token retry", ie);
                    }
                }
            }
            throw new RuntimeException("Failed to get token after " + maxRetries + " retries.");
        }
    }