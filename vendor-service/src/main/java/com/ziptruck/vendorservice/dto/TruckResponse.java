package com.ziptruck.vendorservice.dto;

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
    private List<locationList> locationList;
    private List<orderList> orderIdList;
    private Long vendorId;
}
