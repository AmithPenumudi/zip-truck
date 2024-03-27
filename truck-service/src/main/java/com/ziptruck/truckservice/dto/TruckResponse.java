package com.ziptruck.truckservice.dto;

import com.ziptruck.truckservice.model.locationList;
import com.ziptruck.truckservice.model.orderList;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckResponse {
    private Long truckId;
    private List<locationList> locationList;
    private List<orderList> orderIdList;
    private Long vendorId;
}
