package devsu.msvccustomer.services;

import devsu.msvccustomer.handlers.ResourceNotFoundException;
import devsu.msvccustomer.models.Customer;
import devsu.msvccustomer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService = new CustomerServiceImpl();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> response = customerService.getCustomers();

        assertEquals(customers, response);
    }

    @Test
    void createCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(2L);
        when(customerRepository.existsByCustomerId(2L)).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer response = customerService.createCustomer(customer);

        assertEquals(customer, response);
    }

    @Test
    void getCustomer() throws Exception {
        Long id = 2L;
        Customer customer = new Customer();
        customer.setCustomerId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

       Customer response = customerService.getCustomer(id);

        assertEquals(customer, response);
    }

    @Test
    void updateCustomer() throws Exception {
        Long id = 2L;
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(id);
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        Customer response = customerService.updateCustomer(id, updatedCustomer);

        assertEquals(updatedCustomer, response);
    }

    @Test
    void deleteCustomer() {
        Long id = 2L;
        Customer customer = new Customer();
        customer.setCustomerId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void getCustomerByCustomerId() {
        Long customerId = 2L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        when(customerRepository.findByCustomerId(customerId)).thenReturn(customer);

        Customer foundCustomer = customerService.getCustomerByCustomerId(customerId);

        assertEquals(customer, foundCustomer);
    }

    @Test
    public void getCustomerByCustomerIdNotFound() {
        Long customerId = 2L;
        when(customerRepository.findByCustomerId(customerId)).thenReturn(null);

        try {
            customerService.getCustomerByCustomerId(customerId);
        } catch (ResourceNotFoundException e) {
            assertEquals("Error getting customer: NOT FOUND", e.getMessage());
        }
    }
}