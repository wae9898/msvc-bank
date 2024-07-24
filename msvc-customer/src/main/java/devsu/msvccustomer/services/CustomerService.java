package devsu.msvccustomer.services;

import devsu.msvccustomer.models.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer  createCustomer(Customer customer) throws Exception;

    Customer getCustomer(Long id) throws Exception;

    Customer updateCustomer(Long id, Customer customer) throws Exception ;

    void deleteCustomer(Long id) throws Exception;

    Customer getCustomerByCustomerId(Long customerId);

}
