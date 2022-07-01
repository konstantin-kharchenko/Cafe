package by.kharchenko.cafe.model.service.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.impl.IngredientDaoImpl;
import by.kharchenko.cafe.model.dao.impl.ProductDaoImpl;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.BaseService;
import by.kharchenko.cafe.model.service.ProductService;
import by.kharchenko.cafe.util.filereadwrite.FileReaderWriter;
import by.kharchenko.cafe.validator.ProductValidator;
import by.kharchenko.cafe.validator.impl.ProductValidatorImpl;

import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.EMPTY;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class ProductServiceImpl implements ProductService, BaseService<Product> {
    private static final String PHOTO_PATH_ON_HDD = "D:\\FINAL_PROJECT\\PHOTO\\PRODUCTS\\";
    private static final String FILE_EXTENSION = ".txt";
    private final ProductValidator validator = ProductValidatorImpl.getInstance();
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
        try {
            return ProductDaoImpl.getInstance().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> productData) throws ServiceException {
        boolean isCorrectData = validator.isCorrectCreateData(productData);
        boolean isNameExists;
        ProductDaoImpl productDao = ProductDaoImpl.getInstance();
        try {
            if (isCorrectData) {
                //todo name
                isNameExists = productDao.findIdProductByName(productData.get(NAME)).isPresent();
                if (!isNameExists) {
                    return productDao.add(productData);
                } else {
                    productData.put(NAME, NAME_EXISTS);
                    return false;
                }
            } else {
                if (!Objects.equals(productData.get(NAME), "")) {
                    isNameExists = productDao.findIdProductByName(productData.get(NAME)).isPresent();
                    if (isNameExists) {
                        productData.put(NAME, NAME_EXISTS);
                    }
                    return false;
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Map<String, String> data) throws ServiceException {
        try {
            boolean isCorrectData;
            isCorrectData = validator.isCorrectCreateData(data);
            Integer idProduct = Integer.parseInt(data.get(ID_PRODUCT));
            String name = data.get(NAME);
            Optional<Integer> idAnotherProduct;
            String photoName = data.get(PHOTO_NAME);
            boolean isCorrectPhoto = true;
            if (!photoName.equals("")) {
                isCorrectPhoto = validator.isCorrectPhoto(data.get(PHOTO_NAME));
            }
            if (!data.get(NAME).equals("")) {
                idAnotherProduct = ProductDaoImpl.getInstance().findIdProductByNameAndNotId(name, idProduct);
                if (idAnotherProduct.isPresent()) {
                    data.put(NAME, NAME_EXISTS);
                    if (!photoName.equals("") && !isCorrectPhoto) {
                        data.put(PHOTO, "");
                    } else if (photoName.equals("")) {
                        data.put(PHOTO, EMPTY);
                    }
                    return false;
                }
            }
            if (isCorrectData) {
                if (isCorrectPhoto) {
                    StringBuilder stringBuilder = new StringBuilder(PHOTO_PATH_ON_HDD);
                    stringBuilder.append("\\").append(data.get(ID_PRODUCT)).append(FILE_EXTENSION);
                    String path = stringBuilder.toString();
                    if (!photoName.equals("")) {
                        FileReaderWriter.getInstance().writePhoto(data.get(PHOTO), path);
                    }
                    data.put(PHOTO, path);
                    return ProductDaoImpl.getInstance().update(data);
                } else {
                    if (photoName.equals("")) {
                        data.put(PHOTO, EMPTY);
                    }
                    return false;
                }
            } else {
                if (photoName.equals("")) {
                    data.put(PHOTO, EMPTY);
                }
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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

    @Override
    public Optional<Product> findProductByName(String name) throws ServiceException {
        try {
            return ProductDaoImpl.getInstance().findProductByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteIngredientFromProduct(int idIngredient, int idProduct) throws ServiceException {
        try {
            ProductDaoImpl.getInstance().deleteIngredientFromProduct(idIngredient, idProduct);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeGrams(Map<String, String> data) throws ServiceException {
        boolean isCorrectGrams = validator.isCorrectGrams(data.get(GRAMS));
        if (isCorrectGrams) {
            try {
                ProductDaoImpl.getInstance().changeGrams(data);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addIngredientsIdInProductsIngredientsTableByIdProduct(int idProduct, List<Ingredient> toList) throws ServiceException {
        List<Integer> idList = new ArrayList<>();
        for (Ingredient ingredient : toList) {
            idList.add(ingredient.getIdIngredient());
        }
        try {
            return ProductDaoImpl.getInstance().addIngredientsIdInProductsIngredientsTableByIdProduct(idProduct, idList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
