package com.warys.app.household.application.rest;

import com.warys.app.household.application.request.shopping.CreateListCommand;
import com.warys.app.household.domain.shopping.Item;
import com.warys.app.household.domain.shopping.ItemState;
import com.warys.app.household.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class ShoppingControllerShould extends AbstractControllerShould {

    public static final List<Item> ITEMS = List.of(
            new Item("bread", ItemState.UNKNOWN),
            new Item("bread", ItemState.UNKNOWN)
    );

    @Test
    void create_shopping_list_when_mandatory_fields_are_given() throws Exception {
        final CreateListCommand request = new CreateListCommand(
                LIST_NAME,
                new User(USER_ID, null, null, null),
                ITEMS,
                null
        );
        String content = om.writeValueAsString(request);
        this.mockMvc.perform(
                post("/shopping/list")
                        .content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void NOT_create_shopping_list_when_name_is_not_set() throws Exception {
        String urlTemplate = "/shopping/list";
        final CreateListCommand request = new CreateListCommand(
                null,
                new User(null, null, null, null),
                ITEMS,
                null
        );
        String content = om.writeValueAsString(request);
        this.mockMvc.perform(
                post(urlTemplate)
                        .content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.message", is("[name] : shopping list name is mandatory; ")))
                .andExpect(jsonPath("$.path", is("uri=" + urlTemplate)))
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")));
    }

    @Test
    void NOT_create_shopping_list_when_user_is_not_set() throws Exception {
        String urlTemplate = "/shopping/list";
        final CreateListCommand request = new CreateListCommand(
                LIST_NAME, null, ITEMS, null
        );
        String content = om.writeValueAsString(request);
        this.mockMvc.perform(
                post(urlTemplate)
                        .content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.message", is("[owner] : shopping list owner is mandatory; ")))
                .andExpect(jsonPath("$.path", is("uri=" + urlTemplate)))
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")));
    }

    @Test
    void NOT_create_shopping_list_when_user_id_is_not_set() throws Exception {
        String urlTemplate = "/shopping/list";
        final CreateListCommand request = new CreateListCommand(
                LIST_NAME,
                new User(null, null, null, null),
                ITEMS,
                null
        );
        String content = om.writeValueAsString(request);
        this.mockMvc.perform(
                post(urlTemplate)
                        .content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.message", is("sorry, but you must provide a valid user")))
                .andExpect(jsonPath("$.path", is("uri=" + urlTemplate)))
                .andExpect(jsonPath("$.exception", is("InvalidDataException")));
    }
}