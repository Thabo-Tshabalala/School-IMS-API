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
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private String status; // e.g., 'Pending', 'Shipped', 'Delivered', 'Cancelled'

    protected Order() {}

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.user = builder.user;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
    }

    public long getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
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
                quantity == order.quantity &&
                Objects.equals(user, order.user) &&
                Objects.equals(product, order.product) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, quantity, orderDate, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", product=" + product +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {

        private long orderId;
        private User user;
        private Product product;
        private int quantity;
        private LocalDate orderDate;
        private String status;

        public Builder setOrderId(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
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
            this.user = order.user;
            this.product = order.product;
            this.quantity = order.quantity;
            this.orderDate = order.orderDate;
            this.status = order.status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
