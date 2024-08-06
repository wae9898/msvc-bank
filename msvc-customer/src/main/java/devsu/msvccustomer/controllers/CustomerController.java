package devsu.msvccustomer.controllers;

import devsu.msvccustomer.handlers.ValidateHandler;
import devsu.msvccustomer.models.Customer;
import devsu.msvccustomer.models.dtos.ApiError;
import devsu.msvccustomer.services.CustomerService;
import feign.Response;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customers")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ValidateHandler validateHandler;

    @GetMapping("/hello")
    public String hello() {
        logger. info( "Hello run in msvc-customer");
        return "Hello World!";
    }

    @GetMapping()
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) throws Exception {
       Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomerByCustomerId(@PathVariable Long customerId) {
        return customerService.getCustomerByCustomerId(customerId);
    }

    @PostMapping()
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }

        try {
            Customer customer1 = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @Valid @RequestBody Customer customer,@PathVariable Long id, BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            return validateHandler.validate(result);
        }
        Customer customer1 = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(customer1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred")
            );
        }
    }
}
