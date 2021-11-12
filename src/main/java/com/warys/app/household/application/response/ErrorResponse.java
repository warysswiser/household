package com.warys.app.household.application.response;

import com.warys.app.household.application.common.JacksonRecord;

import java.io.Serializable;
import java.util.Date;

@JacksonRecord
public record ErrorResponse(Date timestamp, String message, String path, String exception)
        implements Serializable {
}
