package com.warys.app.household.application.rest;

import com.warys.app.household.application.response.AppResponse;
import com.warys.app.household.application.request.shopping.CreateListCommand;
import com.warys.app.household.application.request.shopping.UpdateListCommand;
import com.warys.app.household.application.response.shopping.CreateListResponse;
import com.warys.app.household.application.rest.exception.DataNotFoundException;
import com.warys.app.household.application.rest.exception.InvalidDataException;
import com.warys.app.household.domain.ShoppingList;
import com.warys.app.household.domain.shopping.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.warys.app.household.application.response.AppResponse.*;

@RestController
@RequestMapping("/shopping")
@AllArgsConstructor
public class ShoppingController {

    private final CrudService<ShoppingList> shoppingListService;

    @PostMapping(path = "/list")
    AppResponse<CreateListResponse> createShoppingList(@RequestBody @Valid final CreateListCommand request) {
        if (request.owner().id() == null) {
            throw new InvalidDataException("sorry, but you must provide a valid user");
        }
        String response = shoppingListService.create(request);
        return created(new CreateListResponse(response));
    }

    @GetMapping(path = "/list/{shoppingListId}")
    AppResponse<ShoppingList> findShoppingList(@PathVariable @NotBlank final String shoppingListId) {
        try {
            return ok(shoppingListService.find(shoppingListId));
        } catch (IllegalArgumentException ex) {
            throw new DataNotFoundException(ex);
        }
    }

    @PutMapping(path = "/list/{shoppingListId}")
    AppResponse<ShoppingList> updateShoppingList(
            @PathVariable @NotBlank final String shoppingListId,
            @RequestBody @Valid final UpdateListCommand request) {
        return ok(shoppingListService.update(shoppingListId, request));
    }

    @DeleteMapping(path = "/list/{shoppingListId}")
    AppResponse<?> deleteShoppingList(@PathVariable @NotBlank final String shoppingListId) {
        shoppingListService.delete(shoppingListId);
        return deleted();
    }

}
