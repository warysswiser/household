package com.warys.app.household.domain.shopping.service;

import com.warys.app.household.domain.Command;
import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.repository.ShoppingListRepository;

public record ShoppingService(ShoppingListRepository repository) implements CrudService<ShoppingList> {

    @Override
    public String create(Command<ShoppingList> command) {
        ShoppingList created = repository.create(command.toDomain());
        return created.id();
    }

    @Override
    public ShoppingList find(String id) {
        return repository.find(id);
    }

    @Override
    public ShoppingList update(String id, Command<ShoppingList> command) {
        ShoppingList newRecord = command.toDomain();
        return repository.update(id, newRecord);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
