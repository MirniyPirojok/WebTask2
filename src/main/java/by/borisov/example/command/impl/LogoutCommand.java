package by.borisov.example.command.impl;

import by.borisov.example.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;

import static by.borisov.example.command.PagePath.INDEX_PAGE;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return INDEX_PAGE;
    }
}
