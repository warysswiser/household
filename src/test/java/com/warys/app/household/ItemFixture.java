package com.warys.app.household;

import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.shopping.ItemState;

import java.util.List;

public class ItemFixture extends DefaultFixture {

    private static final String ITEM_NAME = "item_name_";

    public static List<Item> anItemList(int count) {
        return lists(count, ItemFixture::anItem);
    }

    private static Item anItem(String index) {
        return new Item( ITEM_NAME + index, ItemState.UNKNOWN);
    }
}
