package com.warys.app.household.domain.user;

import com.warys.app.household.application.common.JacksonRecord;

@JacksonRecord
public record User(String id, String name, String email, String password) {
}
