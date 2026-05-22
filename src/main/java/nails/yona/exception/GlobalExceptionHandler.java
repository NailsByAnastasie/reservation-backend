package nails.yona.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import nails.yona.dto.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ApiErrorDto handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ApiErrorDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Certains champs de votre requête sont invalides.",
                errors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ApiErrorDto handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiErrorDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ApiErrorDto handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Ressource introuvable.",
                ex.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ApiErrorDto handleAccessDeniedException(AccessDeniedException ex) {
        return new ApiErrorDto(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Accès Refusé",
                "Votre session a expiré ou vous n'avez pas les droits nécessaires. Veuillez vous reconnecter.",
                null
        );
    }
}