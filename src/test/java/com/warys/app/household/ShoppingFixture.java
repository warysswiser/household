package com.warys.app.household;

import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;

import java.util.List;

import static com.warys.app.household.ItemFixture.anItemList;
import static com.warys.app.household.UserFixture.aUser;
import static com.warys.app.household.UserFixture.aUserList;

public final class ShoppingFixture extends DefaultFixture {

    private static final String LIST_NAME = "list_name_";

    public static List<ShoppingListEntity> aShoppingLists(int count) {
        return lists(count, ShoppingFixture::aShoppingListEntity);
    }

    public static ShoppingList aShoppingList() {
        return new ShoppingList(DEFAULT_INDEX, LIST_NAME + DEFAULT_INDEX, aUser(DEFAULT_INDEX), anItemList(2), aUserList(2));
    }

    public static ShoppingListEntity aShoppingListEntity() {
        return aShoppingListEntity(DEFAULT_INDEX);
    }

    public static ShoppingListEntity aNotSavedShoppingListEntity() {
        return new ShoppingListEntity(LIST_NAME + DEFAULT_INDEX, aUser(DEFAULT_INDEX), anItemList(2), aUserList(2));
    }

    public static ShoppingList aNotSavedShoppingList() {
        return new ShoppingList(LIST_NAME + DEFAULT_INDEX, aUser(DEFAULT_INDEX), anItemList(2), aUserList(2));
    }

    public static ShoppingListEntity aShoppingListEntity(String index) {
        return new ShoppingListEntity(index, LIST_NAME + index, aUser(index), anItemList(2), aUserList(2));
    }

}
