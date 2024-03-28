package com.ziptruck.truckservice.dto;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TruckRequest {
    private String location;
    private Long orderId;
    private Long vendorId;
}
