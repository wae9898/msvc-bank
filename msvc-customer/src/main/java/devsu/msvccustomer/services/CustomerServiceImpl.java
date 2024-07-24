package devsu.msvccustomer.services;

import devsu.msvccustomer.handlers.ResourceNotFoundException;
import devsu.msvccustomer.models.Customer;
import devsu.msvccustomer.models.dtos.ApiError;
import devsu.msvccustomer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        try{
           return customerRepository.findAll();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public Customer createCustomer(Customer customer) throws Exception {
        if(customerRepository.existsByCustomerId(customer.getCustomerId())) {
            throw new Exception("Duplicate key clienteId violates unique constraint");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByCustomerId(Long customerId) {
        try {
            return customerRepository.findByCustomerId(customerId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error getting customer: NOT FOUND","customerId", customerId.toString());
        }
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {
        return customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Error getting customer: not found"));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setAge(customer.getAge());
        customerToUpdate.setGender(customer.getGender());
        customerToUpdate.setIdentification(customer.getIdentification());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setAddress(customer.getAddress());
        customerToUpdate.setPassword(customer.getPassword());

        return customerRepository.save(customerToUpdate);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found"));
        customerRepository.delete(customer);
    }
}
