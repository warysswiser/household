package com.warys.app.household.infrastructure.config;

import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.repository.ShoppingListRepository;
import com.warys.app.household.domain.shopping.service.CrudService;
import com.warys.app.household.domain.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    CrudService<ShoppingList> shoppingListService(
            @Qualifier("shoppingListMongoRepository") ShoppingListRepository repository) {
        return new ShoppingService(repository);
    }
}
