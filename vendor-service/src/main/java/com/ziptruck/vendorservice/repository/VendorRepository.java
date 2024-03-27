package com.ziptruck.vendorservice.repository;

import com.ziptruck.vendorservice.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
