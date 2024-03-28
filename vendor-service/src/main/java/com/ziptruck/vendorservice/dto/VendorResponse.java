package com.ziptruck.vendorservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorResponse {
    private Long vendorId;
    private String name;
    private String category;
}
