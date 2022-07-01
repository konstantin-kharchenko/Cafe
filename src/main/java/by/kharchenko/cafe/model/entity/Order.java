package by.kharchenko.cafe.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order extends AbstractEntity {
    private int idOrder;
    private String name;
    private LocalDate date;
    private int idClient;
    private BigDecimal price;
    private PaymentType paymentType;
    private List<Product> products = new ArrayList<>();

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public Order(int idOrder, String name, LocalDate date, int idClient, BigDecimal price) {
        this.idOrder = idOrder;
        this.name = name;
        this.date = date;
        this.idClient = idClient;
        this.price = price;
    }

    public Order() {
    }

    public Order(int idClient) {
        this.idClient = idClient;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
        Order order = (Order) o;
        return idOrder == order.idOrder && idClient == order.idClient && name.equals(order.name) && date.equals(order.date) && price.equals(order.price) && paymentType == order.paymentType && products.equals(order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, name, date, idClient, price, paymentType, products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", idClient=" + idClient +
                ", price=" + price +
                ", paymentType=" + paymentType +
                ", products=" + products +
                '}';
    }

}
