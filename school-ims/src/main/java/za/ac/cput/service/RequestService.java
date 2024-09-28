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
        return requestRepository.save(request);
    }

    @Override
    public Request read(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalStateException("Request with Id " + requestId + " does not exist"));
    }

    @Override
    public Request update(Request request) {
        if (requestRepository.existsById(request.getRequestId())) {
            return requestRepository.save(request);
        } else {
            throw new IllegalStateException("Request with Id " + request.getRequestId() + " does not exist");
        }
    }

    @Override
    public boolean delete(Long requestId) {
        if (requestRepository.existsById(requestId)) {
            requestRepository.deleteById(requestId);
            return true;
        } else {
            throw new IllegalStateException("Request with Id " + requestId + " does not exist");
        }
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

//    public void deleteRequestsByProductId(Long productId) {
//        requestRepository.deleteByProduct_ProductId(productId);
//    }
}
