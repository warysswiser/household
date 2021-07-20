package com.warys.app.household.application.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppResponse<T> extends ResponseEntity<T> {

    public AppResponse(T body, HttpStatus status) {
        super(body, status);
    }

    public AppResponse(HttpStatus status) {
        super(status);
    }

    public static <T> AppResponse<T> created(T body) {
        return new AppResponse<>(body, HttpStatus.CREATED);
    }

    public static <T> AppResponse<T> ok(T body) {
        return new AppResponse<>(body, HttpStatus.OK);
    }

    public static <T> AppResponse<T> deleted() {
        return new AppResponse<>(HttpStatus.NO_CONTENT);
    }
}
