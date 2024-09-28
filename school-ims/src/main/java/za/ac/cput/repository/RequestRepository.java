package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {


    List<Request> findByStatus(String status);

    List<Request> findByProduct_ProductId(Long productId);

    void deleteByProduct_ProductId(Long productId);
}
