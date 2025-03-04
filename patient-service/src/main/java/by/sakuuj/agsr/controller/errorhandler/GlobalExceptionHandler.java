package by.sakuuj.agsr.controller.errorhandler;

import by.sakuuj.agsr.dto.response.ApiErrorResponse;
import by.sakuuj.agsr.enums.ErrorCode;
import by.sakuuj.agsr.exceptions.AbstractServiceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String BEARER_AUTHENTICATION_SCHEME = "Bearer";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalError(Exception ex) {

        log.error("INTERNAL ERROR", ex);

        var apiErrorResponse = ApiErrorResponse.builder()
                .errorTime(Instant.now())
                .errorMessage("Internal error")
                .errorCode(ErrorCode.INTERNAL_ERROR)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorResponse);
    }

    @ExceptionHandler({
            TypeMismatchException.class,
            NoResourceFoundException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(Exception ex) {

        log.info("BAD REQUEST");

        var apiErrorResponse = ApiErrorResponse.builder()
                .errorTime(Instant.now())
                .errorMessage(ex.getMessage())
                .errorCode(ErrorCode.INVALID_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }

    @ExceptionHandler({
            AuthenticationException.class,
            OAuth2AuthorizationException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Exception ex) {

        log.info("UNAUTHORIZED");
        var apiErrorResponse = ApiErrorResponse.builder()
                .errorTime(Instant.now())
                .errorMessage("Authentication error")
                .errorCode(ErrorCode.AUTHENTICATION_ERROR)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, BEARER_AUTHENTICATION_SCHEME)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {

        log.info("FORBIDDEN");

        var apiErrorResponse = ApiErrorResponse.builder()
                .errorTime(Instant.now())
                .errorMessage("Access denied")
                .errorCode(ErrorCode.ACCESS_DENIED)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(AbstractServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleAbstractServiceException(AbstractServiceException ex) {

        log.info("NOT FOUND");

        return buildApiErrorResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<ApiErrorResponse> buildApiErrorResponseEntity(
            AbstractServiceException ex,
            HttpStatusCode statusCode
    ) {
        ApiErrorResponse apiErrorResponse = buildApiErrorResponse(ex);

        return ResponseEntity.status(statusCode)
                .body(apiErrorResponse);
    }

    private static ApiErrorResponse buildApiErrorResponse(AbstractServiceException ex) {

        return ApiErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .errorTime(Instant.now())
                .build();
    }
}
