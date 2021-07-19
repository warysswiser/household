package com.warys.app.household.domain.shopping;

import com.warys.app.household.application.common.JacksonRecordMixin;

import java.io.Serializable;

public record Item(String name, ItemState state) implements JacksonRecordMixin, Serializable {
}
