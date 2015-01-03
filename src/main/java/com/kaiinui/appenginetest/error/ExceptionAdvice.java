package com.kaiinui.appenginetest.error;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle exceptions and print responses as JSON Error pack ({code: int, message: String, url: String}) with 500 HTTP status code.
 *
 * code: Refers the exception's {@link org.springframework.web.bind.annotation.ResponseStatus} value.
 * message: Refers the exception's {@code Exception#getLocalizedMessage}
 * url: Refers the exception's {@link com.kaiinui.appenginetest.error.DocUrl}
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        exception.printStackTrace();

        ResponseStatus statusAnnotation = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
        HttpStatus code = statusAnnotation != null ? statusAnnotation.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        String message = exception.getLocalizedMessage();
        if (message == null) { message = ""; }

        DocUrl urlAnnotation = AnnotationUtils.findAnnotation(exception.getClass(), DocUrl.class);
        String url = urlAnnotation != null ? urlAnnotation.value() : "";

        return new ResponseEntity<ErrorResponse>(new ErrorResponse(code.value(), message, url), null, code);
    }
}
