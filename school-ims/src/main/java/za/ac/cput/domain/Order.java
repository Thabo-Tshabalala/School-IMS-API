package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relationship with User

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private String status; // e.g., 'Pending', 'Shipped', 'Delivered', 'Cancelled'

    protected Order() {}

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.user = builder.user; // Relationship with User
        this.orderDate = builder.orderDate;
        this.status = builder.status;
    }

    public long getOrderId() {
        return orderId;
    }

    public User getUser() { // Relationship with User
        return user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                Objects.equals(user, order.user) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, orderDate, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user + // Relationship with User
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {

        private long orderId;
        private User user; // Relationship with User
        private LocalDate orderDate;
        private String status;

        public Builder setOrderId(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setUser(User user) { // Relationship with User
            this.user = user;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Order order) {
            this.orderId = order.orderId;
            this.user = order.user; // Relationship with User
            this.orderDate = order.orderDate;
            this.status = order.status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
