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
    @OneToMany(cascade = CascadeType.ALL)
    private List<locationList> locationList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<orderList> orderIdList;
    private Long vendorId;

}
