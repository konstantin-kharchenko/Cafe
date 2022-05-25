package by.kharchenko.cafe.model.entity;

import java.util.Date;
import java.util.Objects;

public class Order extends AbstractEntity{
    private int idOrder;
    private String name;
    private Date date;
    private int idClient;
    private double sum;

    public Order(int idOrder, String name, Date date, int idClient, double sum) {
        this.idOrder = idOrder;
        this.name = name;
        this.date = date;
        this.idClient = idClient;
        this.sum = sum;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idOrder == order.idOrder && idClient == order.idClient && Double.compare(order.sum, sum) == 0 && Objects.equals(name, order.name) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, name, date, idClient, sum);
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", idClient=" + idClient +
                ", sum=" + sum +
                '}';
    }
}
