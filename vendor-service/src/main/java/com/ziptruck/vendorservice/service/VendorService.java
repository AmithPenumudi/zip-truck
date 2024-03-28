package com.ziptruck.vendorservice.service;


import com.ziptruck.truckservice.service.TruckService;
import com.ziptruck.vendorservice.dto.CustomerResponse;
import com.ziptruck.vendorservice.dto.VendorRequest;
import com.ziptruck.vendorservice.dto.VendorResponse;
import com.ziptruck.vendorservice.model.Vendor;
import com.ziptruck.vendorservice.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@ComponentScan(basePackages = "com.ziptruck.truckservice")
public class VendorService {
    private final VendorRepository vendorRepository;
    private final WebClient webClient;
    private final TruckService truckService;
    public void newVendor(VendorRequest vendorRequest) {
        Vendor vendor = new Vendor();
        vendor.setName(vendorRequest.getName());
        vendor.setCategory(vendorRequest.getCategory());

        vendorRepository.save(vendor);
        log.info("Vendor {} is saved", vendor.getVendorId());
    }

    public List<VendorResponse> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.stream()
                .map(this::mapToVendorResponse)
                .collect(Collectors.toList());
    }

    public VendorResponse getVendorById(Long vendorId) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(vendorId);
        if (optionalVendor.isPresent()) {
            return mapToVendorResponse(optionalVendor.get());
        } else {
            throw new RuntimeException("Vendor not found with id: " + vendorId);
        }
    }

    @Transactional
    public void deleteVendorById(Long vendorId) {
        if (vendorRepository.existsById(vendorId)) {
            vendorRepository.deleteById(vendorId);
            log.info("Vendor with id {} has been deleted", vendorId);
        } else {
            throw new RuntimeException("Vendor not found with id: " + vendorId);
        }
    }

    @Transactional
    public void updateVendorById(Long vendorId, VendorRequest vendorRequest) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(vendorId);
        if (optionalVendor.isPresent()) {
            Vendor vendor = optionalVendor.get();

            if (vendorRequest.getName() != null) {
                vendor.setName(vendorRequest.getName());
            }



            if (vendorRequest.getCategory() != null) {
                vendor.setCategory(vendorRequest.getCategory());
            }

            vendorRepository.save(vendor);
            log.info("Vendor with id {} has been updated", vendorId);
        } else {
            throw new RuntimeException("Vendor not found with id: " + vendorId);
        }
    }

    private VendorResponse mapToVendorResponse(Vendor vendor) {
        return new VendorResponse(
                vendor.getVendorId(),
                vendor.getName(),
                vendor.getCategory()
        );
    }

    public Boolean isAccepting(Long vendorId, Long customerId, Long orderId) {

        // Get the customer location// print vendorId and customerId and orderId
        log.info("In VENDOR VendorId: {}, CustomerId: {}, OrderId: {}", vendorId, orderId, customerId);
        Mono<CustomerResponse[]> customerResponsesMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")  // Specify the host here
                        .port(8081)  // Specify the port here if needed
                        .path("/api/customer")  // Specify the path here
                        .queryParam("customerId", customerId)
                        .build())
                .retrieve()
                .bodyToMono(CustomerResponse[].class);

        customerResponsesMono.subscribe(customerResponses -> {
            // Handle the array of CustomerResponse objects
            for (CustomerResponse customerResponse : customerResponses) {
                log.info("Customer response: {}", customerResponse);
            }
        });

        //print the response
        log.info("Customer response: {}", customerResponsesMono.block());

        //get location from customerResponses
        String location = customerResponsesMono.block()[0].getLocation();
        //print customer location
        log.info("Customer location: {}", location);

        Long truckId = 1L;


        if (truckId != -1) {
            // TruckId found
            truckService.updateOrderList(truckId, orderId);
            return true;
        } else {
            // No truck found with the given location.
            return false;
        }
    }
}
