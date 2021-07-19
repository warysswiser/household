package com.warys.app.household.application.response.shopping;


import com.warys.app.household.application.common.JacksonRecordMixin;

import java.io.Serializable;

public record CreateListResponse(String id) implements Serializable, JacksonRecordMixin {
}
