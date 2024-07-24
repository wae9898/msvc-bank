package devsu.msvcaccount.clients;

import devsu.msvcaccount.models.dtos.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-customer")
public interface CustomerClientRest {
    @GetMapping("/customers/customer/{customerId}")
    Customer getCustomerByCustomerId(@PathVariable Long customerId);
}
