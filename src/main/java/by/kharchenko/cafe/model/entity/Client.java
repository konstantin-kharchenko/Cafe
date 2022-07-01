package by.kharchenko.cafe.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends User {
    private Integer idClient;
    private int loyaltyPoints;
    private boolean isBlock;
    private BigDecimal clientAccount;
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public BigDecimal getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(BigDecimal clientAccount) {
        this.clientAccount = clientAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return loyaltyPoints == client.loyaltyPoints && isBlock == client.isBlock && Objects.equals(idClient, client.idClient) && Objects.equals(clientAccount, client.clientAccount) && Objects.equals(orders, client.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idClient, loyaltyPoints, isBlock, clientAccount, orders);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", loyaltyPoints=" + loyaltyPoints +
                ", isBlock=" + isBlock +
                ", clientAccount=" + clientAccount +
                ", orders=" + orders +
                ", idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", registrationTime=" + registrationTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", photoPath='" + photoPath + '\'' +
                ", stringPhoto='" + stringPhoto + '\'' +
                '}';
    }


    public static class ClientBuilder extends AbstractUserBuilder<Client> {

        public ClientBuilder(Client user) {
            super(user);
        }

        public ClientBuilder() {
        }

        public ClientBuilder withIdClient(int idClient) {
            this.user.idClient = idClient;
            return this;
        }

        public ClientBuilder withLoyaltyPoints(int loyaltyPoints) {
            this.user.loyaltyPoints = loyaltyPoints;
            return this;
        }

        public ClientBuilder withIsBlock(boolean isBlock) {
            this.user.isBlock = isBlock;
            return this;
        }

        public ClientBuilder withClientAccount(BigDecimal clientAccount) {
            this.user.clientAccount = clientAccount;
            return this;
        }

        public Client build() {
            return user;
        }
    }
}
