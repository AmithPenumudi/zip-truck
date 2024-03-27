package com.ziptruck.customerservice.service;

import com.ziptruck.customerservice.dto.CustomerRequest;

import com.ziptruck.customerservice.dto.CustomerResponse;
import com.ziptruck.customerservice.model.Customer;
import com.ziptruck.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void AddCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setMobileNumber(customerRequest.getMobileNumber());
        customer.setAge(customerRequest.getAge());
        customer.setLocation(customerRequest.getLocation());

        customerRepository.save(customer);
        log.info("Customer with id :"+ customer.getCustomerId() +" added");
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customer = customerRepository.findAll();

        return customer.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            return mapToCustomerResponse(optionalCustomer.get());
        } else {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }

    @Transactional
    public void deleteCustomerById(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            log.info("Customer with id {} has been deleted", customerId);
        } else {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }

    @Transactional
    public void updateCustomerById(Long customerId, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (customerRequest.getCustomerName() != null) {
                customer.setCustomerName(customerRequest.getCustomerName());
            }

            if (customerRequest.getMobileNumber() != null) {
                customer.setMobileNumber(customerRequest.getMobileNumber());
            }

            if (customerRequest.getAge() != null) {
                customer.setAge(customerRequest.getAge());
            }

            if (customerRequest.getLocation() != null) {
                customer.setLocation(customerRequest.getLocation());
            }

            customerRepository.save(customer);
            log.info("Customer with id {} has been updated", customerId);
        } else {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }
//    CatalogueResponse[] CatalogueResponseArray =  webClient.get()
//            .uri("http://localhost:8080/api/catalogue")
//            .retrieve()
//            .bodyToMono(CatalogueResponse[].class)
//            .block();
    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getMobileNumber(),
                customer.getAge(),
                customer.getLocation()
        );
    }
}

