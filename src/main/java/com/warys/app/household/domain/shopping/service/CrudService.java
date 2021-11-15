package com.warys.app.household.domain.shopping.service;

import com.warys.app.household.domain.Command;

import java.util.List;

public interface CrudService<T> {

    String create(Command<T> command);

    T find(String id);

    List<T> getAll();

    T update(String id, Command<T> command);

    void delete(String id);
}
