package com.warys.app.household.infrastructure.repository.mongo.entity;

import com.warys.app.household.domain.MappableToDomain;
import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "shopping_list")
public class ShoppingListEntity extends AuditableEntity implements MappableToDomain<ShoppingList> {
    @NotNull(message = "must not be null")
    @Size(min = 4, max = 50, message = "must be between 4 and 50 characters")
    private String name;
    private @NotNull(message = "must not be null") User owner;
    private @NotEmpty(message = "must not be null or empty") List<Item> items;
    private List<User> sharedWith;

    public ShoppingListEntity(String id, String name, User owner, List<Item> items, List<User> sharedWith) {
        super(id);
        this.name = name;
        this.owner = owner;
        this.items = items;
        this.sharedWith = sharedWith;
    }

    @PersistenceConstructor
    public ShoppingListEntity(String id, String name, User owner, List<Item> items, List<User> sharedWith, LocalDateTime creationDate, LocalDateTime updateDate) {
        super(id, creationDate, updateDate);
        this.name = name;
        this.owner = owner;
        this.items = items;
        this.sharedWith = sharedWith;
    }

    @Override
    public ShoppingList toDomain() {
        return new ShoppingList(super.getId(), name, owner, items, sharedWith);
    }

    public static ShoppingListEntity from(ShoppingList domain) {
        return new ShoppingListEntity(
                domain.name(),
                domain.owner(),
                domain.items(),
                domain.sharedWith());
    }
}