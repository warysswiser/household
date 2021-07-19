package com.warys.app.household.it;

import com.warys.app.household.HouseholdApplication;
import com.warys.app.household.application.rest.ShoppingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HouseholdApplicationShould {

    @Autowired
    private ShoppingController shoppingController;

    @Test
    void load_context() {
        assertThat(shoppingController).isNotNull();
    }

    @Test
    void launch_main() {
        HouseholdApplication.main(new String[]{});
    }

}
