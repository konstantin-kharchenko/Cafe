package by.kharchenko.cafe.controller.command;


import by.kharchenko.cafe.controller.command.impl.*;

import java.util.Objects;
import java.util.Optional;

public enum CommandType {
    GO_SIGN_IN_PAGE(new GoSignInPageCommand()),
    GO_REGISTRATION_PAGE(new GoRegistrationPageCommand()),
    GO_HOME_PAGE(new GoHomePageCommand()),
    GO_EXIT_PAGE(new GoExitPageCommand()),
    GO_LANGUAGE_PAGE(new GoLanguagePageCommand()),
    GO_CLIENT_PAGE(new GoClientPageCommand()),
    GO_BASKET_PAGE(new GoBasketPageCommand()),
    GO_CLIENT_ALL_PRODUCTS(new GoClientAllProductsCommand()),
    GO_CLIENT_PROFILE_PAGE(new GoClientProfilePageCommand()),
    GO_USER_PAGE(new GoUserPageCommand()),
    GO_CREATE_ORDER_PAGE(new GoCreateOrderPageCommand()),
    GO_ORDER_PAGE(new GoOrderPageCommand()),
    GO_ALL_ORDERS_PAGE(new GoAllOrdersPageCommand()),
    GO_CHANGE_ORDER_PAGE(new GoChangeOrderPageCommand()),
    GO_PRODUCT_PAGE(new GoProductPageCommand()),
    GO_REFILL_PAGE(new GoRefillPageCommand()),
    ADD_PRODUCT_IN_BASKET(new AddProductInBasketCommand()),
    ADD_FROM_BASKET(new AddFromBasketCommand()),
    DELETE_PRODUCT_FROM_ORDER(new DeleteProductFromOrderCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    SIGN_IN(new SignInCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    DELETE_PRODUCT_FROM_BASKET(new DeleteProductFromBasketCommand()),
    REGISTRATION(new RegistrationCommand()),
    REFILL(new RefillCommand()),
    CHANGE_ORDER(new ChangeOrderCommand()),
    DEFAULT_COMMAND(new DefaultCommand());
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Optional<Command> getCommand(String name) {
        CommandType commandType;
        if (validatedCommand(name)) {
            commandType = CommandType.valueOf(name.toUpperCase());
        } else {
            commandType = CommandType.DEFAULT_COMMAND;
        }
        return Optional.ofNullable(commandType.command);
    }

    private static boolean validatedCommand(String commandStr) {
        if (commandStr != null) {
            for (CommandType command : CommandType.values()) {
                if (Objects.equals(command.toString(), commandStr.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
