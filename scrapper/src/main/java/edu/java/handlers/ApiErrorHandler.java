package edu.java.handlers;

import edu.java.dto.ApiErrorResponse;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
    @ExceptionHandler(WrongParametersException.class)
    public ResponseEntity<ApiErrorResponse> badRequest(WrongParametersException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            "Были переданы не корректные параметры",
            "400",
            "WrongParametersException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ApiResponse(responseCode = "404", description = "Чат не существует")
    @ExceptionHandler(ChatDoesNotExistException.class)
    public ResponseEntity<ApiErrorResponse> chatDidNotFound(ChatDoesNotExistException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            "Такого чата не существует",
            "404",
            "ChatDoesNotExistException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
