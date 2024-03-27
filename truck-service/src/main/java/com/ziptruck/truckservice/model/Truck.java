package com.ziptruck.truckservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name="t_trucks")
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long truckId;
    private List<String> location;
    private String orderId;
    private Long customerId;
    private Long vendorId;

}
