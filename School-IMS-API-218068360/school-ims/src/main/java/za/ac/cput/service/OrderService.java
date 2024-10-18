package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService implements IService<Order, Long> {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order read(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalStateException("Order with Id " + orderId + " does not exist"));
    }

    @Override
    public Order update(Order order) {
        if (orderRepository.existsById(order.getOrderId())) {
            return orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order with Id " + order.getOrderId() + " does not exist");
        }
    }

    @Override
    public boolean delete(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            throw new IllegalStateException("Order with Id " + orderId + " does not exist");
        }
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserID(userId);
    }

    public List<Product> getMostOrderedProducts() {
        List<Order> orders = orderRepository.findAll(); // Fetch all orders
        Map<Product, Integer> productCount = new HashMap<>();

        for (Order order : orders) {
            Product product = order.getProduct();
            productCount.put(product, productCount.getOrDefault(product, 0) + order.getQuantity());
        }

        // Create a list of products sorted by quantity ordered
        return productCount.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

