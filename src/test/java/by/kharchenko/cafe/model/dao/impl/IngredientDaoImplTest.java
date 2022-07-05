package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Ingredient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class IngredientDaoImplTest extends AbstractDaoTest {
    private static final DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private static final String name = "хлеб";
    private static final LocalDate shelfLife = LocalDate.parse("2022-06-05 20:00:00", parser);
    private static final int id = 1;
    private static final boolean block = false;
    private static Ingredient ingredient;
    IngredientDaoImpl ingredientDao = IngredientDaoImpl.getInstance();


    @BeforeMethod
    public void setUp() {
        ingredient = new Ingredient();
        ingredient.setIdIngredient(id);
        ingredient.setShelfLife(shelfLife);
        ingredient.setBlock(block);
        ingredient.setName(name);
    }

    @Test(priority = 0)
    public void testInsert() throws DaoException {
        boolean match = ingredientDao.add(ingredient);
        assertTrue(match);
    }

    @Test(priority = 1, dependsOnMethods = "testInsert")
    public void testFindById() throws DaoException {
        Ingredient ingredient1 = ingredientDao.findIngredientById(ingredient.getIdIngredient()).get();
        assertEquals(ingredient1, ingredient);
    }

    @Test(priority = 2, dependsOnMethods = "testFindById")
    public void testUpdate() throws DaoException {
        boolean match = ingredientDao.update(ingredient);
        assertTrue(match);
    }

    @Test(priority = 3, dependsOnMethods = "testUpdate")
    public void testDelete() throws DaoException {
        boolean match = ingredientDao.delete(ingredient.getIdIngredient());
        assertTrue(match);
    }
}
