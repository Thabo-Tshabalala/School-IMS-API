package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.OrderRepository;

import java.util.List;

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
}
