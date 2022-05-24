package by.kharchenko.cafe.controller;

public final class PageMessage {
    public static final String DEFAULT_MESSAGE = "illegal command";
    public static final String INCORRECT_PASSWORD_OR_LOGIN = "incorrect password or login";
    public static final String INVALIDATE_LOGIN = "invalidate login";
    public static final String INVALIDATE_PASSWORD = "invalidate password";
    public static final String ADMINISTRATOR_STATUS_EXCEPTION = "this user is not activated";
    public static final String LOGIN_EXISTS = "this login already exists";
    public static final String FAILED_TO_ADD_USER = "failed ti add user";
    public static final String SUCCESSFUL_REGISTRATION = "registration completed successful";

    private PageMessage() {
    }
}
