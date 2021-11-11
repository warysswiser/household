package com.warys.app.household.application.rest.handler;

import com.warys.app.household.application.common.AppResponse;
import com.warys.app.household.application.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errors = new StringBuilder();

        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("[%s] : %s; ", error.getField(), error.getDefaultMessage()))
                .forEach(errors::append);
        ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> String.format("[%s] : %s; ", error.getObjectName(), error.getDefaultMessage()))
                .forEach(errors::append);

        var errorResponse = new ErrorResponse(
                new Date(),
                errors.toString(),
                request.getDescription(false), ex.getClass().getSimpleName());

        return handleExceptionInternal(
                ex, errorResponse, headers, BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public final AppResponse<Object> handleAppException(Exception ex, WebRequest request) {
        var errorResponse = new ErrorResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false), ex.getClass().getSimpleName());
        return new AppResponse<>(errorResponse, resolveResponseStatus(ex));
    }

    private HttpStatus resolveResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        return (annotation != null) ? annotation.value() : INTERNAL_SERVER_ERROR;
    }
}
