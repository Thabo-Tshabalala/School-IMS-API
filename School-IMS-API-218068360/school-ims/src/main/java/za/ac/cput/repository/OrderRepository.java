package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders by user ID
    List<Order> findByUser_UserID(long userId);
}
