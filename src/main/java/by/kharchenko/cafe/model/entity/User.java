package by.kharchenko.cafe.model.entity;

import java.util.Date;
import java.util.Objects;

public class User extends AbstractEntity {
    protected int idUser;
    protected String name;
    protected String surname;
    protected String login;
    protected String password;
    protected int age;
    protected Date registrationTime;
    protected String phoneNumber;
    protected String email;
    protected Role role;

    public enum Role {
        CLIENT, ADMINISTRATOR;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        return idUser == user.idUser && age == user.age && Objects.equals(name, user.name) && Objects.equals(surname, user.surname)
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(registrationTime, user.registrationTime)
                && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, name, surname, login, password, age, registrationTime, phoneNumber, email, role);
    }
}
