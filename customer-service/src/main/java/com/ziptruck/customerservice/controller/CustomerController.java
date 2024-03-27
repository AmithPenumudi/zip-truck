package com.ziptruck.customerservice.controller;

import com.ziptruck.customerservice.dto.CustomerRequest;
import com.ziptruck.customerservice.dto.CustomerResponse;
import com.ziptruck.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String AddCustomer(@RequestBody CustomerRequest customerRequest ){
        customerService.AddCustomer(customerRequest);
        return "Customer Added Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers(){

        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomerById(@PathVariable Long customerId) {

        return customerService.getCustomerById(customerId);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomerById(@PathVariable Long customerId) {

        customerService.deleteCustomerById(customerId);
        return "Customer Deleted Successfully";
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String updateCustomerById(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomerById(customerId, customerRequest);
        return "Customer Updated Successfully";
    }
}
