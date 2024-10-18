package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Order; // Ensure that this is the correct Order domain
import za.ac.cput.domain.Request; // Ensure that you have a Request domain
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.OrderRepository; // Ensure that you have an OrderRepository
import za.ac.cput.repository.RequestRepository; // Ensure that you have a RequestRepository

import java.util.List;

@Service
public class ProductService implements IService<Product, Long> {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          OrderRepository orderRepository,
                          RequestRepository requestRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product read(Long aLong) {
        return productRepository.findById(aLong)
                .orElseThrow(() -> new IllegalStateException("Product with Id " + aLong + " does not exist"));
    }

    @Override
    public Product update(Product product) {
        if (productRepository.existsById(product.getProductId())) {
            return productRepository.save(product);
        } else {
            throw new IllegalStateException("Product with Id " + product.getProductId() + " does not exist");
        }
    }

    @Override
    public boolean delete(Long d) {
        if (productRepository.existsById(d)) {

            List<Request> requests = requestRepository.findByProduct_ProductId(d);
            for (Request request : requests) {
                requestRepository.delete(request);
            }


            List<Order> orders = orderRepository.findByProduct_ProductId(d);
            for (Order order : orders) {
                orderRepository.delete(order);
            }


            productRepository.deleteById(d);
            return true;
        } else {
            throw new IllegalStateException("Product with Id " + d + " does not exist");
        }
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
