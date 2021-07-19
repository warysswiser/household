package com.warys.app.household.domain.shopping;

import com.warys.app.household.application.common.JacksonRecordMixin;

public enum ItemState implements JacksonRecordMixin {
    MISSING,
    PROVIDED,
    UNKNOWN
}
