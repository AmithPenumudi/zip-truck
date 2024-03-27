package com.ziptruck.catalogueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogueRequest {
    private String itemName;
    private String itemDescription;
    private Long vendorId;
    private BigDecimal price;
}
