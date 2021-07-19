package com.warys.app.household.it;

import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.infrastructure.config.ValidationConfig;
import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static com.warys.app.household.ItemFixture.anItemList;
import static com.warys.app.household.ShoppingFixture.aNotSavedShoppingListEntity;
import static com.warys.app.household.UserFixture.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(ValidationConfig.class)
class SpringDataShoppingListMongoRepositoryShould {

    @Autowired
    private MongoRepository<ShoppingListEntity, String> tested;

    @Test
    void fill_id_when_shopping_is_created() {
        ShoppingListEntity actual = tested.insert(aNotSavedShoppingListEntity());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void fill_creation_date_when_shopping_is_created() {
        ShoppingListEntity actual = tested.insert(aNotSavedShoppingListEntity());

        assertThat(actual).isNotNull();
        assertThat(actual.getCreationDate()).isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void fill_created_shopping_list() {
        ShoppingListEntity entity = tested.insert(aNotSavedShoppingListEntity());
        ShoppingListEntity actual = tested.findById(entity.getId()).orElse(null);

        assertThat(actual).isNotNull();
        assertThat(actual.getCreationDate()).isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void fill_update_date_when_shopping_is_updated() {
        ShoppingListEntity toUpdate = tested.insert(aNotSavedShoppingListEntity());
        toUpdate.setName("new_list_name");

        ShoppingListEntity actual = tested.save(toUpdate);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("new_list_name");
        assertThat(actual.getUpdateDate()).isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @ParameterizedTest
    @NullSource
    void throw_ConstraintViolationException_when_shopping_with_null_name_is_given(String name) {
        ShoppingListEntity entity = new ShoppingListEntity(name, aUser(), anItemList(2), null);
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> tested.insert(entity)).withMessage("name: must not be null");
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"aze", "akoidusnviqoplkduhqpoejdifhdkdjffkpzieldlddldlmpdhjosp", "ad"})
    void throw_ConstraintViolationException_when_shopping_with_invalid_size_name_is_given(String name) {
        ShoppingListEntity entity = new ShoppingListEntity(name, aUser(), anItemList(2), null);
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> tested.insert(entity)).withMessage("name: must be between 4 and 50 characters");
    }

    @Test
    void throw_ConstraintViolationException_when_shopping_with_invalid_user_is_given() {
        ShoppingListEntity entity = new ShoppingListEntity("list_name", null, anItemList(2), null);
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> tested.insert(entity)).withMessage("owner: must not be null");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void throw_ConstraintViolationException_when_shopping_with_invalid_items_is_given(List<Item> items) {
        ShoppingListEntity entity = new ShoppingListEntity("list_name", aUser(), items, null);
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> tested.insert(entity)).withMessage("items: must not be null or empty");
    }
}