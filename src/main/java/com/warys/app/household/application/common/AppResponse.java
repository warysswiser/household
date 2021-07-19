package com.warys.app.household.application.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppResponse<T> extends ResponseEntity<T> {

    public static <T> AppResponse<T> created(T body) {
        return new AppResponse<>(body, HttpStatus.CREATED);
    }

    public static <T> AppResponse<T> ok(T body) {
        return new AppResponse<>(body, HttpStatus.OK);
    }

    public AppResponse(T body, HttpStatus status) {
        super(body, status);
    }
}
