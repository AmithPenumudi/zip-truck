package com.ziptruck.vendorservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {
    private Long vendorId;
    private String name;
    private Long age;
    private String category;
}
