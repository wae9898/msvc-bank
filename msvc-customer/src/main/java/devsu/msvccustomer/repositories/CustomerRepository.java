package devsu.msvccustomer.repositories;

import devsu.msvccustomer.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    boolean existsByCustomerId(Long clienteId);

    Customer findByCustomerId(Long clienteId);
}
