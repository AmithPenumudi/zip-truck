package com.ziptruck.truckservice.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckResponse {
    private Long truckId;
    private String location;
    private Long orderId;
    private Long vendorId;
}
