package com.warys.app.household.infrastructure.repository.mongo;

import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.repository.ShoppingListRepository;
import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.warys.app.household.ShoppingFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingListMongoRepositoryShould {

    private ShoppingListRepository tested;
    @Mock
    private SpringDataShoppingListMongoRepository repository;

    @BeforeEach
    void before() {
        tested = new ShoppingListMongoRepository(repository);
    }

    @Test
    void create_shopping_list_when_valid_data_are_given() {
        when(repository.insert(any(ShoppingListEntity.class))).thenReturn(aShoppingListEntity());
        ShoppingList actual = tested.create(aShoppingList());

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(DEFAULT_INDEX);
        assertThat(actual.name()).isEqualTo("list_name_" + DEFAULT_INDEX);
        assertThat(actual.owner()).isNotNull();
        assertThat(actual.sharedWith()).isNotEmpty();
    }

    @Test
    void not_be_equals() {
        assertThat(aShoppingListEntity("1")).isNotEqualTo(aShoppingListEntity("2"));
    }

    @Test
    void retrieve_all_shopping_list() {
        when(repository.findAll()).thenReturn(aShoppingListEntities(10));
        List<ShoppingList> actual = tested.list();

        assertThat(actual)
                .isNotNull()
                .hasSize(10)
                .extracting("name")
                .contains("list_name_0", "list_name_1", "list_name_2", "list_name_3", "list_name_4",
                        "list_name_5", "list_name_6", "list_name_7", "list_name_8", "list_name_9");
    }

    @Test
    void find_shopping_list_by_criteria() {
        when(repository.findAll()).thenReturn(aShoppingListEntities(10));
        Predicate<ShoppingList> predicate = s -> s.name().equals("list_name_6");
        Set<ShoppingList> actual = tested.search(predicate);

        assertThat(actual)
                .isNotNull()
                .hasSize(1)
                .extracting("id", "name")
                .contains(tuple("6", "list_name_6"));
    }

    @Test
    void find_shopping_list_by_id() {
        String id = DEFAULT_INDEX;
        when(repository.findById(id)).thenReturn(Optional.of(aShoppingListEntity()));
        ShoppingList actual = tested.find(id);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(id);
        assertThat(actual.name()).isEqualTo("list_name_" + id);
    }

    @Test
    void get_all_shopping() {
        when(repository.findAll()).thenReturn(aShoppingListEntities(5));
        List<ShoppingList> actual = tested.getAll();

        assertThat(actual).hasSize(5);
    }

    @Test
    void update_shopping_list() {
        ShoppingListEntity entity = aShoppingListEntity();
        when(repository.save(any(ShoppingListEntity.class))).thenReturn(entity);
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        ShoppingList actual = tested.update(DEFAULT_INDEX, aShoppingList());

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(DEFAULT_INDEX);
        assertThat(actual.name()).isEqualTo("list_name_" + DEFAULT_INDEX);
        assertThat(actual.owner()).isNotNull();
        assertThat(actual.sharedWith()).isNotEmpty();
    }

    @Test
    void delete_shopping_list() {
        tested.delete(DEFAULT_INDEX);

        verify(repository).deleteById(DEFAULT_INDEX);
    }

    @Test
    void throw_NullPointerException_when_calling_find_with_null_id() {
        assertThatNullPointerException().isThrownBy(() -> tested.find(null));
    }

    @Test
    void throw_IllegalArgumentException_when_calling_find_with_unknown_id() {
        assertThatIllegalArgumentException().isThrownBy(() -> tested.find("unknown_id"));
    }

    @Test
    void throw_NullPointerException_null_data_is_given() {
        assertThatNullPointerException().isThrownBy(() -> tested.create(null));
    }
}