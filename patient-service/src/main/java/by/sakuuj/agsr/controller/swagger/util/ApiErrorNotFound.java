package by.sakuuj.agsr.controller.swagger.util;

import by.sakuuj.agsr.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "404",
        description = "Not found",
        content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject("""
                        {
                          "errorTime": "2000-01-01T23:07:59",
                          "errorType": "ENTITY_NOT_FOUND",
                          "errorMessage": "Entity is not found"
                        }"""
                )
        )
)
public @interface ApiErrorNotFound {
}
