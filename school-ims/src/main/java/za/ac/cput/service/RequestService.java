package za.ac.cput.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Request;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.RequestRepository;

import java.util.List;

@Service
public class RequestService implements IService<Request, Long> {

    private final RequestRepository requestRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    @Autowired
    public RequestService(RequestRepository requestRepository, ProductRepository productRepository) {
        this.requestRepository = requestRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Request create(Request request) {

        logger.info("Incoming request: {}", request);
        Product product = request.getProduct();
        if (product == null || product.getProductId() == 0) {
            logger.error("Product ID must be provided");
            throw new IllegalArgumentException("Product ID must be provided");
        }
        Product loadedProduct = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        logger.info("Product found: {}", loadedProduct);
        request.setProduct(loadedProduct);
        Request savedRequest = requestRepository.save(request);
        logger.info("Request saved: {}", savedRequest);

        return savedRequest;
    }

    @Override
    public Request read(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalStateException("Request with Id " + requestId + " does not exist"));
    }

    @Override
    public Request update(Request request) {
        if (!requestRepository.existsById(request.getRequestId())) {
            throw new IllegalStateException("Request with Id " + request.getRequestId() + " does not exist");
        }
        return requestRepository.save(request);
    }

    @Override
    public boolean delete(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new IllegalStateException("Request with Id " + requestId + " does not exist");
        }
        requestRepository.deleteById(requestId);
        return true;
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    public List<Request> getRequestsByProductId(Long productId) {
        return requestRepository.findByProduct_ProductId(productId);
    }

    //wait
//    public void deleteRequestsByProductId(Long productId) {
//        requestRepository.deleteByProduct_ProductId(productId);
//    }
}
