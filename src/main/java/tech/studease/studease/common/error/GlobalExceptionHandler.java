package tech.studease.studease.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static tech.studease.studease.common.util.ValidationUtils.getErrorResponseOfFieldErrors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.studease.studease.domain.collections.exception.CollectionAlreadyExistsException;
import tech.studease.studease.domain.collections.exception.CollectionInUseException;
import tech.studease.studease.domain.sessions.exception.TestSessionAlreadyExistsException;
import tech.studease.studease.domain.users.exception.TokenExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(WebRequest request) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(UNAUTHORIZED.value())
            .error(UNAUTHORIZED.getReasonPhrase())
            .message("Wrong password")
            .path(request.getDescription(false).substring(4))
            .build();
    return ResponseEntity.status(UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler({TokenExpiredException.class, AuthorizationDeniedException.class})
  public ResponseEntity<ErrorResponse> handleAuthorizationException(
      RuntimeException exc, WebRequest request) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(UNAUTHORIZED.value())
            .error(UNAUTHORIZED.getReasonPhrase())
            .message(exc.getMessage())
            .path(request.getDescription(false).substring(4))
            .build();
    return ResponseEntity.status(UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(
      EntityNotFoundException exc, WebRequest request) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(NOT_FOUND.value())
            .error(NOT_FOUND.getReasonPhrase())
            .message(exc.getMessage())
            .path(request.getDescription(false).substring(4))
            .build();
    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler({
    CollectionAlreadyExistsException.class,
    IllegalArgumentException.class,
    TestSessionAlreadyExistsException.class,
    IllegalStateException.class,
    CollectionInUseException.class
  })
  public ResponseEntity<ErrorResponse> handleBadRequestException(
      RuntimeException exc, WebRequest request) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(BAD_REQUEST.value())
            .error(BAD_REQUEST.getReasonPhrase())
            .message(exc.getMessage())
            .path(request.getDescription(false).substring(4))
            .build();
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exc,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    return ResponseEntity.status(BAD_REQUEST)
        .body(getErrorResponseOfFieldErrors(exc.getBindingResult().getAllErrors(), request));
  }
}
