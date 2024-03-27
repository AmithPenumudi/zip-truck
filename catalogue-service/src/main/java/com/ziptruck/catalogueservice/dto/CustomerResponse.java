package com.ziptruck.catalogueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private long customerId;
    private String customerName;
    private String mobileNumber;
    private Long age;
    private String location;
}
