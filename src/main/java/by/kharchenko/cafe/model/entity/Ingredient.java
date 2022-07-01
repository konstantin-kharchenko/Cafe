package by.kharchenko.cafe.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Ingredient extends AbstractEntity implements Cloneable {
    private int idIngredient;
    private String name;
    private LocalDate shelfLife;
    private double grams;
    private boolean block;

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


    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return idIngredient == that.idIngredient && Double.compare(that.grams, grams) == 0 && block == that.block && Objects.equals(name, that.name) && Objects.equals(shelfLife, that.shelfLife);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, name, shelfLife, grams, block);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", name='" + name + '\'' +
                ", shelfLife=" + shelfLife +
                ", grams=" + grams +
                ", block=" + block +
                '}';
    }
}
