package com.warys.app.household.domain;

import com.warys.app.household.application.common.JacksonRecord;
import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.user.User;

import java.util.List;

@JacksonRecord
public record ShoppingList(String id, String name, User owner, List<Item> items, List<User> sharedWith) {

    public ShoppingList(String name, User owner, List<Item> items, List<User> sharedWith) {
        this(null, name, owner, items, sharedWith);
    }
}
