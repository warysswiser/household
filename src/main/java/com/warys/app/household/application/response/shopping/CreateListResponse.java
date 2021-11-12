package com.warys.app.household.application.response.shopping;


import com.warys.app.household.application.common.JacksonRecord;

import java.io.Serializable;

@JacksonRecord
public record CreateListResponse(String id) implements Serializable {
}
