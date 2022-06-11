package by.kharchenko.cafe.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Ingredient extends AbstractEntity implements Cloneable {
    private int idIngredient;
    private String name;
    private LocalDate shelfLife;
    private double grams;

    public Ingredient(int idIngredient, String name, LocalDate shelfLife) {
        this.idIngredient = idIngredient;
        this.name = name;
        this.shelfLife = shelfLife;
    }

    public Ingredient() {
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idProduct) {
        this.idIngredient = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient product = (Ingredient) o;
        return idIngredient == product.idIngredient && Double.compare(product.grams, grams) == 0 && Objects.equals(name, product.name) && Objects.equals(shelfLife, product.shelfLife);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, name, shelfLife, grams);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", name='" + name + '\'' +
                ", shelfLife=" + shelfLife +
                ", grams=" + grams +
                '}';
    }
}
