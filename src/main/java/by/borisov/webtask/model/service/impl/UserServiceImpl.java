package by.borisov.webtask.model.service.impl;

import by.borisov.webtask.model.service.UserService;

public class UserServiceImpl implements UserService {
    private static final String ADMIN_LOGIN = "1";
    private static final String ADMIN_PASS = "1";

    public boolean checkUser(String login, String password) {
        return ADMIN_LOGIN.equals(login) && ADMIN_PASS.equals(password);
    }
}