package by.kharchenko.cafe.model.dao;

public final class SqlQuery {
    public static final String SELECT_LOGIN_PASS = "SELECT password FROM users WHERE login = ?";
    public static final String SELECT_USER_BY_USER_ID = "SELECT id_user, name, surname, login, password, email, age, registration_time, phone_number, role FROM users WHERE id_user = ?";
    public static final String SELECT_ADMINISTRATOR_BY_USER_ID = "SELECT id_administrator, experience, status, information, id_user FROM administrators WHERE id_user = ?";
    public static final String SELECT_CLIENT_BY_USER_ID = "SELECT id_client, is_block, loyalty_points, payment_type, photo, id_user FROM clients WHERE id_user = ?";
    public static final String ADD_CLIENT = "INSERT INTO clients(is_block, loyalty_points, payment_type, id_user) VALUES (?, ?, ?, ?)";
    public static final String ADD_USER = "INSERT INTO users(name, surname, login, password, email, age, registration_time, phone_number, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_ADMINISTRATOR = "INSERT INTO administrators(experience, status, id_user) VALUES (?, ?, ?)";
    public static final String ID_BY_LOGIN = "SELECT id_user FROM users WHERE login = ?";
    public static final String ROLE_BY_LOGIN = "SELECT role FROM users WHERE login = ?";
    public static final String SELECT_LOGINS = "SELECT login FROM users";
    public static final String SELECT_ALL_MENU = "SELECT id_menu, name, date, photo FROM menu";
    public static final String SELECT_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, sum FROM orders WHERE id_client = ?";
    public static final String SELECT_MENU_ID_BY_ORDER_ID = "SELECT id_menu FROM orders_menu WHERE id_order = ?";
    public static final String SELECT_MENU_LIST_BY_ID_LIST = "SELECT id_menu, name, date, photo FROM menu WHERE id_menu IN (";
    private SqlQuery() {
    }
}
