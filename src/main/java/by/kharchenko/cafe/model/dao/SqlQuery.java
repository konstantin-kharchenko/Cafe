package by.kharchenko.cafe.model.dao;

public final class SqlQuery {
    public static final String SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login = ?";
    public static final String SELECT_ROLE_BY_LOGIN = "SELECT role FROM users WHERE login = ?";
    public static final String SELECT_ID_USER_BY_LOGIN = "SELECT id_user FROM users WHERE login = ?";
    public static final String SELECT_USER_BY_USER_ID = "SELECT id_user, name, surname, login, password, email, birthday, " +
            "registration_time, phone_number, role, photo FROM users WHERE id_user = ?";
    public static final String SELECT_CLIENT_BY_USER_ID = "SELECT id_client, is_block, loyalty_points, id_user, client_account " +
            "FROM clients WHERE id_user = ?";
    public static final String SELECT_ADMINISTRATOR_BY_USER_ID = "SELECT id_administrator, experience, status, information, " +
            "id_user FROM administrators WHERE id_user = ?";
    public static final String SELECT_NEW_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, price, payment_type FROM " +
            "orders a WHERE id_client = ? ORDER BY a.date DESC LIMIT 5 OFFSET 0";
    public static final String SELECT_PRODUCTS_ID_BY_ORDERS_ID = "SELECT id_order, id_product FROM orders_products WHERE id_order IN (";
    public static final String SELECT_INGREDIENTS_ID_BY_PRODUCTS_ID = "SELECT id_ingredient, grams, id_product FROM " +
            "products_ingredients WHERE id_product IN (";
    public static final String SELECT_PRODUCTS_BY_ID_PRODUCTS = "SELECT id_product, name, date, photo, price FROM products " +
            "WHERE id_product IN (";
    public static final String SELECT_NEW_PRODUCTS = "SELECT id_product, name, photo, date, price FROM products " +
            "a ORDER BY a.date DESC LIMIT 10 OFFSET 0";
    public static final String SELECT_PRODUCTS_BY_PAGE = "SELECT id_product, name, photo, date, price FROM products a " +
            "ORDER BY a.date DESC LIMIT 10 OFFSET ?";
    public static final String SELECT_COUNT_PRODUCTS = "SELECT COUNT(id_product) FROM products";
    public static final String ADD_USER = "INSERT INTO users(name, surname, login, password, email, birthday, " +
            "registration_time, phone_number, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_CLIENT = "INSERT INTO clients (id_user) VALUES (?)";
    public static final String ADD_ADMINISTRATOR = "INSERT INTO administrators(id_user) VALUES (?)";
    public static final String SELECT_ORDER_ID_BY_ID_CLIENT_AND_ORDER_NAME = "SELECT id_order FROM orders WHERE id_client = ? AND name = ?";
    public static final String UPDATE_USER_WHERE_NOT_NEW_PHOTO_BY_USER_ID = "UPDATE users SET name = ?, surname = ?," +
            " login = ?, phone_number = ? WHERE id_user = ?";
    public static final String UPDATE_USER_WHERE_NEW_PHOTO_BY_USER_ID = "UPDATE users SET name = ?, surname = ?, " +
            "login = ?, phone_number = ?, photo = ? WHERE id_user = ?";
    public static final String SELECT_ID_USER_WHERE_LOGIN_AND_NOT_ID_USER = "SELECT id_user FROM final_project_cafe.users " +
            "where login = ? and id_user != ?";
    public static final String ADD_ORDER = "INSERT INTO orders(name, date, id_client, payment_type) VALUES (?, ?, ?, ?)";
    public static final String SELECT_ORDER_BY_ORDER_ID = "SELECT id_order, name, date, id_client, price, payment_type " +
            "FROM orders WHERE id_order = ?";
    public static final String SELECT_PRODUCTS_ID_BY_ORDER_ID = "SELECT id_product FROM orders_products WHERE id_order = ?";
    public static final String SELECT_INGREDIENTS_BY_ID_LIST = "SELECT id_ingredient, name, shelf_life FROM ingredients WHERE " +
            "id_ingredient IN (";
    public static final String SELECT_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, price, payment_type FROM " +
            "orders a WHERE id_client = ? ORDER BY a.date DESC";
    public static final String DELETE_PRODUCT_FROM_ORDER = "DELETE FROM orders_products WHERE id_product = ? AND id_order = ?";
    public static final String SELECT_PRODUCTS_PRICE_BY_ID_PRODUCTS = "SELECT price FROM products WHERE id_product IN (";
    public static final String UPDATE_ORDER_PRICE_BY_ORDER_ID = "UPDATE orders SET price = ? WHERE id_order = ?";
    public static final String DELETE_ORDER_BY_ID_FROM_ORDERS_TABLE = "DELETE FROM orders WHERE id_order = ?";
    public static final String DELETE_ORDER_BY_ID_FROM_ORDERS_PRODUCTS_TABLE = "DELETE FROM orders_products WHERE id_order = ?";
    public static final String INSERT_PRODUCTS_ID_BY_ORDER_ID = "INSERT INTO orders_products ( id_order, id_product ) VALUES";
    public static final String SELECT_PRODUCT_BY_ID_PRODUCT = "SELECT id_product, name, photo, date, price FROM products WHERE id_product = ?";
    public static final String SELECT_INGREDIENTS_ID_BY_PRODUCT_ID = "SELECT id_ingredient, grams, id_product FROM " +
            "products_ingredients WHERE id_product = ?";
    public static final String SELECT_ORDER_ID_BY_ID_CLIENT_AND_ORDER_NAME_AND_NOT_ID_ORDER = "SELECT id_order FROM " +
            "orders WHERE id_client = ? AND name = ? AND id_order != ?";
    public static final String UPDATE_ORDER = "UPDATE orders SET name = ?, payment_type = ?, date = ? WHERE id_order = ?";
    public static final String SELECT_CLIENT_ACCOUNT_BI_CLIENT_ID = "SELECT client_account FROM clients WHERE id_client = ?";
    public static final String INSERT_CLIENT_ACCOUNT_BI_CLIENT_ID = "UPDATE clients SET client_account = ? WHERE id_client = ?";
    private SqlQuery() {
    }
}
