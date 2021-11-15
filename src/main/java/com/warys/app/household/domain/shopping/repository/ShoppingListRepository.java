package com.warys.app.household.domain.shopping.repository;

import com.warys.app.household.domain.ShoppingList;

import java.util.List;

public interface ShoppingListRepository extends SearchByPredicate<ShoppingList> {

    ShoppingList create(ShoppingList record);

    ShoppingList find(String id);

    List<ShoppingList> getAll();

    ShoppingList update(String id, ShoppingList toDomain);

    void delete(String id);
}
