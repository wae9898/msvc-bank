package devsu.msvccustomer.services;

import devsu.msvccustomer.handlers.ResourceNotFoundException;
import devsu.msvccustomer.models.Customer;
import devsu.msvccustomer.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        try{
           return customerRepository.findAll();
        }catch (Exception e){
            logger.error("Customer empty list, error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) throws Exception {
        try{
            if(customerRepository.existsByCustomerId(customer.getCustomerId())) {
                logger.error("Duplicate key clienteId violates unique constraint");
                throw new Exception("Duplicate key clienteId violates unique constraint");
            }
            return customerRepository.save(customer);
        }catch (Exception e){
            logger.error("An error occurred while creating a customer, message: " + e.getMessage());
            throw new Exception("An error occurred while creating a customer, message:" + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerByCustomerId(Long customerId) {
        try {
            return customerRepository.findByCustomerId(customerId);
        } catch (Exception e) {
            logger.error("Error getting customer: " + e.getMessage());
            throw new ResourceNotFoundException("Error getting customer: NOT FOUND","customerId", customerId.toString());
        }
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {
        try{
            return customerRepository.findById(id)
                    .orElseThrow(() -> new Exception("Error getting customer: not found"));
        }catch(Exception e){
            logger.error("Error getting customer: " + e.getMessage());
            throw new ResourceNotFoundException("Error getting customer: ", "customerId", id.toString());
        }
    }

    @Transactional
    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        try{
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
        }catch (Exception e){
            logger.error("Error updating customer");
            throw new Exception("Error updating customer:" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) throws Exception {
        try{
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new Exception("Customer not found"));
            customerRepository.delete(customer);
        }catch (Exception e){
            logger.error("Error deleting customer");
            throw new Exception("Error deleting customer:" + e.getMessage());
        }
    }
}
