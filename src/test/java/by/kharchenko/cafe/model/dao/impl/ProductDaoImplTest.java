package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Product;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductDaoImplTest extends AbstractDaoTest{

    private static final DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String name = "лапша";
    private static final LocalDate date = LocalDate.parse("2022-07-30", parser);
    private static final String photo = "D:\\FINAL_PROJECT\\PHOTO\\PRODUCTS\\16.txt";
    private static final int id = 1;
    private static final BigDecimal price = new BigDecimal("4.50");
    private static Product product;
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();


    @BeforeMethod
    public void setUp() {
       product = new Product();
       product.setIdProduct(id);
       product.setDate(date);
       product.setPrice(price);
       product.setName(name);
    }

    @Test(priority = 0)
    public void testInsert() throws DaoException {
        boolean match = productDao.add(product);
        assertTrue(match);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindById() throws DaoException {
        Product product1 = productDao.findProductByProductId(product.getIdProduct()).get();
        assertEquals(product1, product);
    }

    @Test(priority = 2, dependsOnMethods = "testFindById")
    public void testUpdate() throws DaoException {
        product.setPhoto(photo);
        assertTrue(productDao.update(product));
    }

    @Test(priority = 3, dependsOnMethods = "testUpdate")
    public void testDelete() throws DaoException {
        product.setPhoto(photo);
        assertTrue(productDao.delete(product.getIdProduct()));
    }
}
