package com.warys.app.household.domain.shopping.service;

import com.warys.app.household.domain.Command;

public interface CrudService<T> {

    String create(Command<T> command);

    T find(String id);

    T update(String id, Command<T> command);
}
