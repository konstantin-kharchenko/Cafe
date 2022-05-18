package by.kharchenko.cafe.model.entity;

import java.util.Objects;

public final class Client extends User {

    private int idClient;
    private int loyaltyPoints;
    private PaymentType paymentType;
    private String photo;
    private boolean isBlock;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return loyaltyPoints == client.loyaltyPoints && isBlock == client.isBlock && paymentType == client.paymentType && photo.equals(client.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loyaltyPoints, paymentType, photo, isBlock);
    }

    @Override
    public String toString() {
        return "Client{" +
                "loyaltyPoints=" + loyaltyPoints +
                ", paymentType=" + paymentType +
                ", photo='" + photo + '\'' +
                ", isBlock=" + isBlock +
                ", idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", registrationTime=" + registrationTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public enum PaymentType {
        CLIENT_ACCOUNT("client_account"),
        CASH("cash");

        private final String payment;

        PaymentType(String status) {
            this.payment = status;
        }

        public String getStatus() {
            return payment;
        }
    }

    public static class ClientBuilder extends AbstractUserBuilder<Client> {


        public ClientBuilder(Client user) {
            super(user);
        }

        public ClientBuilder() {
        }

        public ClientBuilder withLoyaltyPoints(int loyaltyPoints) {
            this.user.loyaltyPoints = loyaltyPoints;
            return this;
        }

        public ClientBuilder withPaymentType(PaymentType paymentType) {
            this.user.paymentType = paymentType;
            return this;
        }

        public ClientBuilder withPhoto(String photo) {
            this.user.photo = photo;
            return this;
        }

        public ClientBuilder withIsBlock(boolean isBlock) {
            this.user.isBlock = isBlock;
            return this;
        }
    }
}
