package com.warys.app.household.infrastructure.repository.mongo;

import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.repository.ShoppingListRepository;
import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ShoppingListMongoRepository implements ShoppingListRepository {

    private final SpringDataShoppingListMongoRepository repository;

    public ShoppingList create(ShoppingList record) {
        Objects.requireNonNull(record, "shopping list given for creation must not be null");
        return repository.insert(ShoppingListEntity.from(record)).toDomain();
    }

    public ShoppingList find(String id) {
        return findEntity(id).toDomain();
    }

    @Override
    public List<ShoppingList> getAll() {
        return list();
    }

    @Override
    public ShoppingList update(String id, ShoppingList record) {
        ShoppingListEntity entity = findEntity(id);
        Objects.requireNonNull(record, "shopping list given for update must not be null");
        entity.setName(record.name());
        entity.setOwner(record.owner());
        entity.setItems(record.items());
        entity.setSharedWith(record.sharedWith());
        return repository.save(entity).toDomain();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<ShoppingList> list() {
        return repository.findAll().stream().map(ShoppingListEntity::toDomain).collect(Collectors.toList());
    }

    private ShoppingListEntity findEntity(String id) {
        Objects.requireNonNull(id, "Given id must not be null");
        return repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("sorry, this shopping list is unknown"));
    }
}
