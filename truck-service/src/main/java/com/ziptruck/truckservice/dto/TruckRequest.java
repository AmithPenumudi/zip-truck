package com.ziptruck.truckservice.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TruckRequest {
    private List<String> location;
    private String orderId;
    private Long customerId;
    private Long vendorId;
}
