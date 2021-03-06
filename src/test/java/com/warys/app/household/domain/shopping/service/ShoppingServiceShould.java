package com.warys.app.household.domain.shopping.service;

import com.warys.app.household.domain.Command;
import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.repository.ShoppingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.warys.app.household.DefaultFixture.DEFAULT_INDEX;
import static com.warys.app.household.ShoppingFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceShould {

    CrudService<ShoppingList> tested;
    @Mock
    private Command<ShoppingList> command;
    @Mock
    private ShoppingListRepository repository;

    @BeforeEach
    void before() {
        tested = new ShoppingService(repository);
    }

    @Test
    void create_shopping_list_when_valid_data_are_given() {
        when(repository.create(any(ShoppingList.class))).thenReturn(aShoppingList());
        when(command.toDomain()).thenReturn(aNotSavedShoppingList());

        String actual = tested.create(command);

        assertThat(actual).isNotBlank().isEqualTo(DEFAULT_INDEX);
    }

    @Test
    void find_shopping_list_when_valid_id_is_given() {
        String id = DEFAULT_INDEX;
        when(repository.find(id)).thenReturn(aShoppingList());

        ShoppingList actual = tested.find(id);

        assertThat(actual).isNotNull();
        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(id);
        assertThat(actual.name()).isEqualTo("list_name_" + id);
    }

    @Test
    void get_all_shopping_list() {
        String id = DEFAULT_INDEX;
        when(repository.getAll()).thenReturn(aShoppingLists(5));

        List<ShoppingList> actual = tested.getAll();

        assertThat(actual).hasSize(5);
    }

    @Test
    void update_shopping_list_when_valid_id_is_given() {
        when(repository.update(anyString(), any(ShoppingList.class))).thenReturn(aShoppingList());
        when(command.toDomain()).thenReturn(aNotSavedShoppingList());

        ShoppingList actual = tested.update(DEFAULT_INDEX, command);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isNotBlank().isEqualTo(DEFAULT_INDEX);
    }

    @Test
    void delete_shopping_list_when_valid_id_is_given() {
        tested.delete(DEFAULT_INDEX);

        verify(repository).delete(DEFAULT_INDEX);
    }
}