package com.ziptruck.truckservice.repository;

import com.ziptruck.truckservice.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findByVendorId(Long vendorId);
}
