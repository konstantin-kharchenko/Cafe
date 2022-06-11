package by.kharchenko.cafe.controller.command;


public class Router {
    private String page;
    private Type type = Type.FORWARD;

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public Router(String page) {
        this.page = page;
    }

    public enum Type {
        FORWARD, REDIRECT
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRedirect() {
        this.type = Type.REDIRECT;
    }

    public Type getType() {
        return type;
    }

    public void setForward() {
        this.type = Type.FORWARD;
    }
}
