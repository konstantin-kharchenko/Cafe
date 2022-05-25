package by.kharchenko.cafe.model.entity;

import java.util.Date;
import java.util.Objects;

public class Menu extends AbstractEntity {
    private int idMenu;
    private String name;
    private Date date;
    private String photo;

    public Menu(int idMenu, String name, Date registrationTime, String photo) {
        this.idMenu = idMenu;
        this.name = name;
        this.date = registrationTime;
        this.photo = photo;
    }

    public Menu() {
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return idMenu == menu.idMenu && Objects.equals(name, menu.name) && Objects.equals(date, menu.date) && Objects.equals(photo, menu.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMenu, name, date, photo);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "idMenu=" + idMenu +
                ", name='" + name + '\'' +
                ", registrationTime=" + date +
                ", photo='" + photo + '\'' +
                '}';
    }
}
