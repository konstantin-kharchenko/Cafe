package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.IngredientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.cafe.model.dao.impl.ProductDaoImpl;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.ProductService;
import by.kharchenko.cafe.util.filereadwrite.FileReaderWriter;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductServiceImpl implements ProductService, BaseService<Product> {
    private static final String photoPath = "D:\\FINAL_PROJECT\\PHOTO\\PRODUCTS\\";
    private static final String FILE_EXTENSION = ".txt";
    private static final ProductServiceImpl instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductServiceImpl getInstance() {
        return instance;
    }


    @Override
    public boolean delete(Product product) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {
        return false;
    }

    @Override
    public List<Product> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> t) throws ServiceException {
        return false;
    }

    @Override
    public List<Product> findNewProducts() throws ServiceException {
        try {
            List<Product> products = ProductDaoImpl.getInstance().findNewProducts();
            for (Product product : products) {
                String stringPhoto = FileReaderWriter.getInstance().readPhoto(product.getPhoto());
                product.setStringPhoto(stringPhoto);
            }
            return products;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findProductsByIdOrder(int idOrder) throws ServiceException {
        try {
            List<Integer> productsId = ProductDaoImpl.getInstance().findProductsIdByOrderId(idOrder);
            List<Product> products = ProductDaoImpl.getInstance().findProductsByIdProducts(productsId);
            for (Product product : products) {
                String stringPhoto = FileReaderWriter.getInstance().readPhoto(product.getPhoto());
                product.setStringPhoto(stringPhoto);
            }
            IngredientDaoImpl.getInstance().addIngredientsInProducts(products);
            return products;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countProducts() throws ServiceException {
        try {
            return ProductDaoImpl.getInstance().countProducts();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findProductsByPageNumber(int currentPage) throws ServiceException {
        try {
            return ProductDaoImpl.getInstance().findProductsByPageNumber(currentPage);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Product> findProductByProductId(int parseInt) throws ServiceException {
        try {
            Optional<Product> product = ProductDaoImpl.getInstance().findProductByProductId(parseInt);
            if (product.isPresent()) {
                IngredientDaoImpl.getInstance().addIngredientsInProduct(product.get());
            }
            String stringPhoto = FileReaderWriter.getInstance().readPhoto(product.get().getPhoto());
            product.get().setStringPhoto(stringPhoto);
            return product;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
