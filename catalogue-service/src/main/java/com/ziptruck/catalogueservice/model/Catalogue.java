package com.ziptruck.catalogueservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "t_catalogue")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class Catalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Long vendorId;
    private BigDecimal price;
}
