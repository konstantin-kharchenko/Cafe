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
    public static final String SELECT_NEW_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, price, payment_type, id_client FROM " +
            "orders a WHERE id_client = ? ORDER BY a.date DESC LIMIT 5 OFFSET 0";
    public static final String SELECT_PRODUCTS_ID_BY_ORDERS_ID = "SELECT id_order, id_product FROM orders_products WHERE id_order IN (";
    public static final String SELECT_INGREDIENTS_BY_PRODUCTS_ID = "SELECT id_ingredient, grams, id_product FROM " +
            "products_ingredients WHERE id_product IN (";

    public static final String SELECT_PRODUCTS_BY_ID_PRODUCTS = "SELECT id_product, name, date, photo, price FROM products " +
            "WHERE id_product IN (";
    public static final String SELECT_PRODUCT_BY_NAME = "SELECT id_product, name, date, photo, price FROM products " +
            "WHERE name = ?";
    public static final String SELECT_INGREDIENT_BY_NAME = "SELECT id_ingredient, name, shelf_life, block FROM ingredients " +
            "WHERE name = ?";
    public static final String SELECT_INGREDIENT_BY_ID = "SELECT id_ingredient, name, shelf_life, block FROM ingredients " +
            "WHERE id_ingredient = ?";
    public static final String SELECT_NEW_PRODUCTS = "SELECT id_product, name, photo, date, price FROM products " +
            "a ORDER BY a.date DESC LIMIT 10 OFFSET 0";
    public static final String SELECT_PRODUCTS_BY_PAGE = "SELECT id_product, name, photo, date, price FROM products a " +
            "ORDER BY a.date DESC LIMIT 10 OFFSET ?";
    public static final String SELECT_INGREDIENTS_BY_PAGE = "SELECT id_ingredient, name, shelf_life, block FROM ingredients a " +
            "ORDER BY a.shelf_life DESC LIMIT 10 OFFSET ?";
    public static final String SELECT_COUNT_PRODUCTS = "SELECT COUNT(id_product) FROM products";
    public static final String SELECT_COUNT_INGREDIENTS = "SELECT COUNT(id_ingredient) FROM ingredients";
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
    public static final String ADD_PRODUCT = "INSERT INTO products(name, date, price) VALUES (?, ?, ?)";
    public static final String ADD_INGREDIENT = "INSERT INTO ingredients(name, shelf_life) VALUES (?, ?)";
    public static final String SELECT_ORDER_BY_ORDER_ID = "SELECT id_order, name, date, id_client, price, payment_type, id_client " +
            "FROM orders WHERE id_order = ?";
    public static final String SELECT_PRODUCTS_ID_BY_ORDER_ID = "SELECT id_product FROM orders_products WHERE id_order = ?";
    public static final String SELECT_INGREDIENTS_ID_BY_PRODUCT_ID = "SELECT id_ingredient FROM products_ingredients WHERE id_product = ?";
    public static final String SELECT_INGREDIENTS_BY_ID_LIST = "SELECT id_ingredient, name, shelf_life, block FROM ingredients WHERE " +
            "id_ingredient IN (";
    public static final String SELECT_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, price, payment_type, id_client FROM " +
            "orders a WHERE id_client = ? ORDER BY a.date DESC";
    public static final String DELETE_PRODUCT_FROM_ORDER = "DELETE FROM orders_products WHERE id_product = ? AND id_order = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id_product = ?";
    public static final String SELECT_PRODUCTS_PRICE_BY_ID_PRODUCTS = "SELECT price FROM products WHERE id_product IN (";
    public static final String UPDATE_ORDER_PRICE_BY_ORDER_ID = "UPDATE orders SET price = ? WHERE id_order = ?";
    public static final String UPDATE_INGREDIENT_BY_ID = "UPDATE ingredients SET name = ?, shelf_life = ? WHERE id_ingredient = ?";
    public static final String DELETE_ORDER_BY_ID_FROM_ORDERS_TABLE = "DELETE FROM orders WHERE id_order = ?";
    public static final String DELETE_ORDER_BY_ID_FROM_ORDERS_PRODUCTS_TABLE = "DELETE FROM orders_products WHERE id_order = ?";
    public static final String INSERT_PRODUCTS_ID_BY_ORDER_ID = "INSERT INTO orders_products ( id_order, id_product ) VALUES";
    public static final String INSERT_INGREDIENTS_ID_BY_PRODUCT_ID = "INSERT INTO products_ingredients ( id_product, id_ingredient ) VALUES";
    public static final String SELECT_PRODUCT_BY_ID_PRODUCT = "SELECT id_product, name, photo, date, price " +
            "FROM products WHERE id_product = ?";
    public static final String SELECT_INGREDIENTS_BY_PRODUCT_ID = "SELECT id_ingredient, grams, id_product FROM " +
            "products_ingredients WHERE id_product = ?";
    public static final String SELECT_ORDER_ID_BY_ID_CLIENT_AND_ORDER_NAME_AND_NOT_ID_ORDER = "SELECT id_order FROM " +
            "orders WHERE id_client = ? AND name = ? AND id_order != ?";
    public static final String UPDATE_ORDER = "UPDATE orders SET name = ?, payment_type = ?, date = ? WHERE id_order = ?";
    public static final String SELECT_CLIENT_ACCOUNT_BI_CLIENT_ID = "SELECT client_account FROM clients WHERE id_client = ?";
    public static final String INSERT_CLIENT_ACCOUNT_BI_CLIENT_ID = "UPDATE clients SET client_account = ? WHERE id_client = ?";
    public static final String SELECT_OVERDUE_ORDERS = "SELECT id_order, name, date, price, payment_type, " +
            "id_client FROM orders WHERE date < ?";
    public static final String SELECT_CLIENTS_BY_CLIENTS_ID = "SELECT id_client, is_block, loyalty_points, id_user, client_account " +
            "FROM clients WHERE id_client IN (";
    public static final String SELECT_USERS_BY_USERS_ID = "SELECT id_user, name, surname, login, password, email, birthday, " +
            "registration_time, phone_number, role, photo FROM users WHERE id_user IN (";
    public static final String SELECT_USER_BY_USERS_ID = "SELECT id_user, name, surname, login, password, email, birthday, " +
            "registration_time, phone_number, role, photo FROM users WHERE id_user  = ?";
    public static final String SELECT_USERS_BY_PAGE = "SELECT id_user, name, surname, login, password, email, birthday, " +
            "registration_time, phone_number, role, photo FROM users a WHERE role = 'client' " +
            "ORDER BY a.id_user DESC LIMIT 10 OFFSET ?";
    public static final String SELECT_CLIENTS_BY_PAGE = "SELECT id_client, is_block, loyalty_points, id_user, client_account " +
            "FROM clients b " +
            "ORDER BY b.id_user DESC LIMIT 10 OFFSET ?";
    public static final String SELECT_COUNT_CLIENTS = "SELECT COUNT(id_client) FROM clients";
    public static final String UPDATE_ADMINISTRATOR_EXPERIENCE_BY_USER_ID = "UPDATE administrators SET experience = ? WHERE id_user = ?";
    public static final String SELECT_TODAY_ORDERS = "SELECT id_order, name, date, price, payment_type, id_client FROM " +
            "orders WHERE date = ?";
    public static final String UNLOCK_CLIENT_BY_CLIENT_ID = "UPDATE clients SET is_block = 0 WHERE id_client = ?";
    public static final String LOCK_CLIENT_BY_CLIENT_ID = "UPDATE clients SET is_block = 1 WHERE id_client = ?";
    public static final String SELECT_LOYALTY_POINTS_BI_CLIENT_ID = "SELECT loyalty_points FROM clients WHERE id_client = ?";
    public static final String UPDATE_LOYALTY_POINTS_BI_CLIENT_ID = "UPDATE clients SET loyalty_points = ? WHERE id_client = ?";
    public static final String SELECT_OVERDUE_ORDERS_BY_CLIENT_ID = "SELECT id_order, name, date, price, payment_type, " +
            "id_client FROM orders WHERE date < ? AND id_client = ?";
    public static final String SELECT_CLIENT_BY_CLIENTS_ID = "SELECT id_client, is_block, loyalty_points, id_user, client_account " +
            "FROM clients WHERE id_client = ?";
    public static final String SELECT_CLIENT_ACCOUNT_BY_CLIENT_ID = "SELECT client_account FROM clients WHERE id_client = ?";
    public static final String SELECT_LOYALTY_POINTS_BY_CLIENT_ID = "SELECT loyalty_points FROM clients WHERE id_client = ?";
    public static final String UPDATE_LOYALTY_POINTS_BY_CLIENT_ID = "UPDATE clients SET loyalty_points = ? WHERE id_client = ?";
    public static final String SELECT_PRICE_BY_ORDER_ID = "SELECT price FROM orders WHERE id_order = ?";
    public static final String SELECT_PAYMENT_TYPE_BY_ORDER_ID = "SELECT payment_type FROM orders WHERE id_order = ?";
    public static final String UPDATE_CLIENT_ACCOUNT_BY_CLIENT_ID = "UPDATE clients SET client_account = ? WHERE id_client = ?";
    public static final String SELECT_ADMINISTRATOR_APPLICATIONS = "SELECT id_administrator, experience, status, information," +
            " id_user FROM administrators WHERE status = 'waiting'";
    public static final String APPROVAL_ADMINISTRATOR_BY_ID = "UPDATE administrators SET status = 'accepted' WHERE id_administrator = ?";
    public static final String SELECT_PRODUCT_ID_BY_PRODUCT_NAME = "SELECT id_product FROM products WHERE name = ?";
    public static final String UPDATE_PRODUCT_WHERE_NOT_NEW_PHOTO_BY_PRODUCT_ID = "UPDATE products SET name = ?, date = ?," +
            " price = ? WHERE id_product = ?";
    public static final String UPDATE_PRODUCT_WHERE_NEW_PHOTO_BY_PRODUCT_ID = "UPDATE products SET name = ?, date = ?," +
            " price = ?, photo = ? WHERE id_product = ?";
    public static final String SELECT_ID_PRODUCT_WHERE_NAME_AND_NOT_ID_PRODUCT = "SELECT id_product FROM products " +
            "where name = ? and id_product != ?";
    public static final String SELECT_ID_INGREDIENT_WHERE_NAME_AND_NOT_ID_PRODUCT = "SELECT id_ingredient FROM ingredients " +
            "where name = ? and id_ingredient != ?";
    public static final String DELETE_INGREDIENT_FROM_PRODUCT = "DELETE FROM products_ingredients WHERE id_product = ? AND id_ingredient = ?";
    public static final String UPDATE_GRAMS_FROM_PRODUCTS_INGREDIENTS = "UPDATE products_ingredients SET grams = ? WHERE id_product = ? AND id_ingredient = ?";
    public static final String BLOCK_INGREDIENT_BY_ID = "UPDATE ingredients SET block = 1 WHERE id_ingredient = ?";
    public static final String SELECT_GRAMS_FROM_PRODUCTS_INGREDIENTS = "SELECT grams FROM products_ingredients WHERE id_product = ? AND id_ingredient = ?";

    private SqlQuery() {
    }
}
