package za.ac.cput.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "requests")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private long requestId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Relationship with Product

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status; // eg 'Pending', 'Approved', 'Rejected'

    protected Request() {}

    private Request(Builder builder) {
        this.requestId = builder.requestId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.status = builder.status;
    }

    public long getRequestId() {
        return requestId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Request setRequestId(long requestId) {
        this.requestId = requestId;
        return this;
    }

    public Request setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Request setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Request setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return requestId == request.requestId &&
                quantity == request.quantity &&
                Objects.equals(product, request.product) &&
                Objects.equals(status, request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, product, quantity, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {

        private long requestId;
        private Product product;
        private int quantity;
        private String status;

        public Builder setRequestId(long requestId) {
            this.requestId = requestId;
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

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Request request) {
            this.requestId = request.requestId;
            this.product = request.product;
            this.quantity = request.quantity;
            this.status = request.status;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
