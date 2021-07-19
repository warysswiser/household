package com.warys.app.household.application.rest;

import com.warys.app.household.application.request.shopping.CreateListCommand;
import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.shopping.ItemState;
import com.warys.app.household.domain.user.User;
import com.warys.app.household.infrastructure.repository.mongo.SpringDataShoppingListMongoRepository;
import com.warys.app.household.infrastructure.repository.mongo.entity.ShoppingListEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.warys.app.household.DefaultFixture.DEFAULT_INDEX;
import static com.warys.app.household.ShoppingFixture.aShoppingListEntity;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class GetShoppingControllerShould extends AbstractControllerShould {

    @MockBean
    public SpringDataShoppingListMongoRepository repository;
    public static final ShoppingListEntity A_SHOPPING_LIST_ENTITY = aShoppingListEntity(SHOPPING_LIST_ID);

    @BeforeEach
    void before() {
        when(repository.findById(SHOPPING_LIST_ID)).thenReturn(Optional.of(A_SHOPPING_LIST_ENTITY));
        when(repository.save(any(ShoppingListEntity.class))).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void get_shopping_list_when_known_id_is_given() throws Exception {
        String urlTemplate = "/shopping/list/" + SHOPPING_LIST_ID;

        this.mockMvc.perform(
                get(urlTemplate)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("list_name_" + SHOPPING_LIST_ID)))
                .andExpect(jsonPath("$.owner", notNullValue()))
                .andExpect(jsonPath("$.items", notNullValue()))
                .andExpect(jsonPath("$.sharedWith", notNullValue()));
    }

    @Test
    void NOT_get_shopping_list_when_unknown_id_is_given() throws Exception {
        String urlTemplate = "/shopping/list/" + DEFAULT_INDEX;

        this.mockMvc.perform(
                get(urlTemplate)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.message", is("sorry, this shopping list is unknown")))
                .andExpect(jsonPath("$.path", is("uri=" + urlTemplate)))
                .andExpect(jsonPath("$.exception", is("DataNotFoundException")));
    }


    @Test
    void update_shopping_list() throws Exception {
        String urlTemplate = "/shopping/list/" + SHOPPING_LIST_ID;

        final CreateListCommand request = new CreateListCommand(
                LIST_NAME,
                new User(USER_ID,null, null, null),
                List.of(
                        new Item( "bread", ItemState.UNKNOWN),
                        new Item( "bread", ItemState.UNKNOWN)
                ),
                null
        );
        String content = om.writeValueAsString(request);
        this.mockMvc.perform(
                put(urlTemplate)
                        .content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
}