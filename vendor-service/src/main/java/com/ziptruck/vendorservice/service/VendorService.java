package com.ziptruck.vendorservice.service;


import com.ziptruck.truckservice.service.TruckService;
import com.ziptruck.vendorservice.dto.CustomerResponse;
import com.ziptruck.vendorservice.dto.TruckResponse;
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
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));

        CustomerResponse customerResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://localhost:8081/api/customer")
                        .queryParam("customerId", customerId)
                        .build())
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();
        String location = customerResponse.getLocation();
        TruckResponse[] truckResponseArray = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://localhost:8083/api/truck")
                        .queryParam("vendorId", vendorId)
                        .build())
                .retrieve()
                .bodyToMono(TruckResponse[].class)
                .block();
        Long truckId = Arrays.stream(truckResponseArray)
                .filter(truckResponse -> truckResponse.getLocation().contains(location))
                .mapToLong(TruckResponse::getTruckId)
                .findFirst()
                .orElse(-1); // Provide a default value if no truckId is found

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
