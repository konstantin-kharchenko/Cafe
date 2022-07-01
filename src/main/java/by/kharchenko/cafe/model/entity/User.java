package by.kharchenko.cafe.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class User extends AbstractEntity {
    protected Integer idUser;
    protected String name;
    protected String surname;
    protected String login;
    protected String password;
    protected LocalDate birthday;
    protected Date registrationTime;
    protected String phoneNumber;
    protected String email;
    protected Role role;
    protected String photoPath;
    protected String stringPhoto;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photo) {
        this.photoPath = photo;
    }

    public String getStringPhoto() {
        return stringPhoto;
    }

    public void setStringPhoto(String stringPhoto) {
        this.stringPhoto = stringPhoto;
    }

    public enum Role {
        CLIENT, ADMINISTRATOR;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser && birthday == user.birthday && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(registrationTime, user.registrationTime) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && role == user.role && Objects.equals(photoPath, user.photoPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, name, surname, login, password, birthday, registrationTime, phoneNumber, email, role, photoPath);
    }
}

