package com.warys.app.household.infrastructure.repository.mongo;

import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShoppingListMongoRepository extends MongoRepository<ShoppingListEntity, String> {
}
