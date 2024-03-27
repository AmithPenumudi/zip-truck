package com.ziptruck.catalogueservice.repository;

import com.ziptruck.catalogueservice.model.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
    List<Catalogue> findByItemNameInAndVendorId(List<String> itemName, Long vendorId);
}
