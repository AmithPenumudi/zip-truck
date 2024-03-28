package com.ziptruck.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogueResponse {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Long vendorId;
    private BigDecimal price;
}
