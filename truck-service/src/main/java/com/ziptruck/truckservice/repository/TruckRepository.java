package com.ziptruck.truckservice.repository;

import com.ziptruck.truckservice.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}
