package by.kharchenko.cafe.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Product extends AbstractEntity {
    private Integer idProduct;
    private String name;
    private LocalDate date;
    private String photo;
    private BigDecimal price;
    protected String stringPhoto;

    private List<Ingredient> ingredients = new ArrayList<>();

    public Product(Integer idProduct, String name, LocalDate registrationTime, String photo, BigDecimal price) {
        this.idProduct = idProduct;
        this.name = name;
        this.date = registrationTime;
        this.photo = photo;
        this.price = price;
    }

    public Product() {
    }

    public String getStringPhoto() {
        return stringPhoto;
    }

    public void setStringPhoto(String stringPhoto) {
        this.stringPhoto = stringPhoto;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idMenu) {
        this.idProduct = idMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return idProduct == product.idProduct && Objects.equals(name, product.name) && Objects.equals(date, product.date) && Objects.equals(photo, product.photo) && Objects.equals(price, product.price) && Objects.equals(ingredients, product.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, name, date, photo, price, ingredients);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }
}
