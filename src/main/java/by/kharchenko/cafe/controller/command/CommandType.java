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
    GO_ALL_PRODUCTS(new GoAllProductsPageCommand()),
    GO_CLIENT_PROFILE_PAGE(new GoClientProfilePageCommand()),
    GO_USER_PAGE(new GoUserPageCommand()),
    GO_CREATE_ORDER_PAGE(new GoCreateOrderPageCommand()),
    GO_ORDER_PAGE(new GoOrderPageCommand()),
    GO_ADD_PRODUCT_PAGE(new GoAddProductPageCommand()),
    GO_ADD_INGREDIENT_PAGE(new GoAddIngredientCommand()),
    GO_ALL_ORDERS_PAGE(new GoAllOrdersPageCommand()),
    GO_ALL_INGREDIENTS(new GoAllIngredientsPageCommand()),
    GO_CHANGE_ORDER_PAGE(new GoChangeOrderPageCommand()),
    GO_CHANGE_INGREDIENT_PAGE(new GoChangeIngredientCommand()),
    GO_PRODUCT_PAGE(new GoProductPageCommand()),
    GO_ADMINISTRATOR_PROFILE_PAGE(new GoAdministratorProfilePageCommand()),
    GO_REFILL_PAGE(new GoRefillPageCommand()),
    GO_CHANGE_PRODUCT_PAGE(new GoChangeProductPageCommand()),
    GO_ADMINISTRATOR_BASKET_PAGE(new GoAdministratorBasketPageCommand()),
    GO_CHANGE_GRAMS_PAGE(new GoChangeGramsPageCommand()),
    CLIENT(new ClientCommand()),
    ADD_PRODUCT_IN_BASKET(new AddProductInBasketCommand()),
    ADD_INGREDIENTS_FROM_ADMINISTRATOR_BASKET(new AddIngredientsFromAdministratorBasketCommand()),
    ADD_PRODUCT(new AddProductCommand()),
    ADD_INGREDIENT(new AddIngredientCommand()),
    ADD_FROM_BASKET(new AddFromBasketCommand()),
    ADD_TO_ADMINISTRATOR_BASKET(new AddToAdministratorBasketCommand()),
    DELETE_PRODUCT_FROM_ORDER(new DeleteProductFromOrderCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    DELETE_PRODUCT(new DeleteProductCommand()),
    DELETE_INGREDIENT(new DeleteIngredientCommand()),
    CONFIRM_RECEIPT_OF_ORDER(new ConfirmReceiptOfOrderCommand()),
    REJECT_ORDER(new RejectOrderCommand()),
    OVERDUE_CLIENT_ORDERS(new OverdueClientOrdersCommand()),
    SEARCH_CLIENT(new SearchClientCommand()),
    SEARCH_PRODUCT(new SearchProductCommand()),
    ADMINISTRATOR_APPLICATIONS(new AdministratorApplicationsCommand()),
    ADMIN_APPROVAL(new AdministratorApprovalCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    UNLOCK(new UnlockCommand()),
    UPDATE_CLIENT(new UpdateClientCommand()),
    UPDATE_ADMINISTRATOR(new UpdateAdministratorCommand()),
    DELETE_INGREDIENT_FROM_BASKET(new DeleteIngredientFromBasketCommand()),
    FIND_CLIENT(new FindClientCommand()),
    SIGN_IN(new SignInCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    CHANGE_INGREDIENT(new ChangeIngredientCommand()),
    DELETE_PRODUCT_FROM_BASKET(new DeleteProductFromBasketCommand()),
    REGISTRATION(new RegistrationCommand()),
    REFILL(new RefillCommand()),
    CHANGE_ORDER(new ChangeOrderCommand()),
    CHANGE_PRODUCT(new ChangeProductCommand()),
    SEARCH_INGREDIENT(new SearchIngredientCommand()),
    DELETE_INGREDIENT_FROM_PRODUCT(new DeleteIngredientFromProductCommand()),
    CHANGE_GRAMS(new ChangeGramsCommand()),
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
