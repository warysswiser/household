package com.warys.app.household.domain.shopping;

import com.warys.app.household.application.common.JacksonRecord;

import java.io.Serializable;

@JacksonRecord
public record Item(String name, ItemState state) implements Serializable {
}
