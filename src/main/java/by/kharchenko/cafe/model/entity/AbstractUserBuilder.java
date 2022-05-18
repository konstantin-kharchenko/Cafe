package by.kharchenko.cafe.model.entity;

import java.util.Date;

public abstract class AbstractUserBuilder<T extends User> {
    protected T user;

    public AbstractUserBuilder(T user) {
        this.user = user;
    }

    public AbstractUserBuilder() {
    }

    public AbstractUserBuilder<T> withIdUser(int idClient) {
        user.idUser = idClient;
        return this;
    }

    public AbstractUserBuilder<T> withName(String name) {
        user.name = name;
        return this;
    }

    public AbstractUserBuilder<T> withSurname(String surname) {
        user.surname = surname;
        return this;
    }

    public AbstractUserBuilder<T> withLogin(String login) {
        user.login = login;
        return this;
    }

    public AbstractUserBuilder<T> withPassword(String password) {
        user.password = password;
        return this;
    }

    public AbstractUserBuilder<T> withEmail(String email) {
        user.email = email;
        return this;
    }

    public AbstractUserBuilder<T> withAge(int age) {
        user.age = age;
        return this;
    }

    public AbstractUserBuilder<T> withRegistrationTime(Date registrationTime) {
        user.registrationTime = registrationTime;
        return this;
    }

    public AbstractUserBuilder<T> withPhoneNumber(String phoneNumber) {
        user.phoneNumber = phoneNumber;
        return this;
    }

    public T build() {
        return user;
    }
}
