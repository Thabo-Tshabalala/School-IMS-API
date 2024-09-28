package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Request;
import za.ac.cput.repository.RequestRepository;

import java.util.List;

@Service
public class RequestService implements IService<Request, Long> {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request create(Request request) {
        // Ensure the product is set before saving
        if (request.getProduct() == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        return requestRepository.save(request);
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

    // Uncomment if you need to delete requests by product ID
//    public void deleteRequestsByProductId(Long productId) {
//        requestRepository.deleteByProduct_ProductId(productId);
//    }
}
