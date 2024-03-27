package com.ziptruck.vendorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckResponse {
    private long truckId;
    private List<String> location;
    private String orderId;
    private Long customerId;
    private Long vendorId;
}