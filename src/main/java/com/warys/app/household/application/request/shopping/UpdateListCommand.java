package com.warys.app.household.application.request.shopping;

import com.warys.app.household.domain.Command;
import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.user.User;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public record UpdateListCommand(
        @NotBlank(message = "shopping list name is mandatory") String name,
        @NotNull(message = "shopping list owner is mandatory") User owner,
        @NotEmpty(message = "at least one item must be set for shopping list creation") List<Item> items,
        @Nullable List<User> sharedWith)
        implements Command<ShoppingList>, Serializable {


    @Override
    public ShoppingList toDomain() {
        return new ShoppingList(name, owner, items, sharedWith);
    }
}
