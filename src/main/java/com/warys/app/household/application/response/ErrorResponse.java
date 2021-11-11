package com.warys.app.household.application.response;

import com.warys.app.household.application.common.JacksonRecordMixin;

import java.io.Serializable;
import java.util.Date;

public record ErrorResponse(Date timestamp, String message, String path, String exception)
        implements Serializable, JacksonRecordMixin {
}
