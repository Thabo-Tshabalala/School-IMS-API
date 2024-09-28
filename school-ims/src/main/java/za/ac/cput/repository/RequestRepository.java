package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
