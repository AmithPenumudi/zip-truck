package com.ziptruck.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private long customerId;
    private String customerName;
    private String mobileNumber;
    private Long age;
    private String location;
}
