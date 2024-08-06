package devsu.msvccustomer.controllers;

import devsu.msvccustomer.models.Customer;
import devsu.msvccustomer.services.CustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void getCustomerById() throws Exception {
        Long id = 2L;
        Customer customer = new Customer();
        customer.setCustomerId(id);
        ResponseEntity<Customer> responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
        when(customerService.getCustomer(anyLong())).thenAnswer(invocation -> responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(id));
    }

}