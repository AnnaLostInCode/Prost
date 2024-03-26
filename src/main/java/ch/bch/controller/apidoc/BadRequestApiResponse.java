package ch.bch.controller.apidoc;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(responseCode = "400", description = "Bei fehlerhafter Aufruf-Syntax und fehlenden oder falsch geformten Parametern. " + "Die Antwort-Payload gibt Aufschluss über den genauen Fehler. "
        + "Falls dem Fehler ein API-Errorcode zugeordnet ist, wird dieser ausgegeben.",
        content = @Content(schema = @Schema(implementation = Object.class)))
public @interface BadRequestApiResponse {
}
