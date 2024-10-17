package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {


    List<Request> findByStatus(String status);

    List<Request> findByProduct_ProductId(Long productId);
    Optional<Request> findByProduct(Product product);
    void deleteByProduct_ProductId(Long productId);
}
