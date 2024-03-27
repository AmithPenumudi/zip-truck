package com.ziptruck.vendorservice.controller;


import com.ziptruck.vendorservice.dto.VendorRequest;
import com.ziptruck.vendorservice.dto.VendorResponse;
import com.ziptruck.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newVendor(@RequestBody VendorRequest vendorRequest){
        vendorService.newVendor(vendorRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VendorResponse> getAllVendors(){
        return vendorService.getAllVendors();
    }
    @GetMapping("/vendor")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isAccepting(@RequestParam Long vendorId, @RequestParam Long customerId, @RequestParam Long orderId){
        return vendorService.isAccepting(vendorId, customerId, orderId);
    }
    @GetMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorResponse getVendorById(@PathVariable Long vendorId) {
        return vendorService.getVendorById(vendorId);
    }

    @DeleteMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVendorById(@PathVariable Long vendorId) {
        vendorService.deleteVendorById(vendorId);
    }

    @PutMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVendorById(@PathVariable Long vendorId, @RequestBody VendorRequest vendorRequest) {
        vendorService.updateVendorById(vendorId, vendorRequest);
    }
}
