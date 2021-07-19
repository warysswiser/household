package com.warys.app.household;

import com.warys.app.household.domain.user.User;

import java.util.List;

public final class UserFixture extends DefaultFixture {

    public static List<User> aUserList(int count) {
        return lists(count, UserFixture::aUser);
    }

    public static User aUser() {
        return aUser(DEFAULT_INDEX);
    }

    public static User aUser(String index) {
        return new User(index, "user_name_" + index, "email@dmain.com", "password");
    }
}
