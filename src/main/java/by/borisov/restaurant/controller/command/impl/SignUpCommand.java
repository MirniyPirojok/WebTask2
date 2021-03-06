package by.borisov.restaurant.controller.command.impl;

import by.borisov.restaurant.controller.command.ActionCommand;
import by.borisov.restaurant.controller.command.ParameterName;
import by.borisov.restaurant.controller.command.PagePath;
import by.borisov.restaurant.exception.ServiceException;
import by.borisov.restaurant.model.service.UserService;
import by.borisov.restaurant.model.service.impl.UserServiceImpl;
import by.borisov.restaurant.util.FormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static by.borisov.restaurant.controller.command.ParameterName.FORM_PARAM_MESSAGE_ERROR;
import static by.borisov.restaurant.controller.command.PagePath.MAIN_PAGE;
import static by.borisov.restaurant.model.dao.ColumnName.EMAIL;
import static by.borisov.restaurant.model.dao.ColumnName.NAME;
import static by.borisov.restaurant.model.dao.ColumnName.PASSWORD;
import static by.borisov.restaurant.model.dao.ColumnName.PHONE;
import static by.borisov.restaurant.model.dao.ColumnName.SURNAME;

/**
 * Class allows the guest to sign up
 */

public class SignUpCommand implements ActionCommand {
    static Logger logger = LogManager.getLogger();

    /**
     * @param request from browser
     * @return String page
     */

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.setAttribute(ParameterName.TYPE_METHOD, ParameterName.GET);
        if (!FormValidator.isPost(request)) {
            page = PagePath.SIGN_UP_PAGE;
        } else {
            Map<String, String> userParameters = new HashMap<>();
            userParameters.put(EMAIL, request.getParameter(EMAIL));
            userParameters.put(PASSWORD, request.getParameter(PASSWORD));
            userParameters.put(PHONE, request.getParameter(PHONE));
            userParameters.put(NAME, request.getParameter(NAME));
            userParameters.put(SURNAME, request.getParameter(SURNAME));

            UserService userService = new UserServiceImpl();
            try {
                userService.createUser(userParameters);
                HttpSession session = request.getSession();
                session.setAttribute(ParameterName.FORM_PARAM_USER_NAME, userParameters.get(NAME));
                page = MAIN_PAGE;
                logger.debug(String.format("User %s was registered. Go to main page.", EMAIL));
            } catch (ServiceException e) {
                request.setAttribute(FORM_PARAM_MESSAGE_ERROR, "Can't sign up.");
                logger.error("Error when SignUpCommand", e);
                page = PagePath.SIGN_UP_PAGE;
            }
        }
        return page;
    }
}
