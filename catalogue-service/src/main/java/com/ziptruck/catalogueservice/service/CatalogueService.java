package com.ziptruck.catalogueservice.service;

import com.ziptruck.catalogueservice.dto.CatalogueRequest;
import com.ziptruck.catalogueservice.dto.CatalogueResponse;
import com.ziptruck.catalogueservice.model.Catalogue;
import com.ziptruck.catalogueservice.repository.CatalogueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogueService {
    private final CatalogueRepository catalogueRepository;
    public void createCatalogue(CatalogueRequest catalogueRequest) {
        Catalogue catalogue = new Catalogue();
        catalogue.setItemName(catalogueRequest.getItemName());
        catalogue.setItemDescription(catalogueRequest.getItemDescription());
        catalogue.setVendorId(catalogueRequest.getVendorId());
        catalogue.setPrice(catalogueRequest.getPrice());

        catalogueRepository.save(catalogue);
        log.info("Product {} is saved", catalogue.getItemId());
    }

    public List<CatalogueResponse> getAllCatalogue() {
        List<Catalogue> catalogue = catalogueRepository.findAll();

        return catalogue.stream()
                .map(this::mapToCatalogueResponse)
                .collect(Collectors.toList());

    }
    public CatalogueResponse getCatalogueById(Long itemId) {
    Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(itemId);
    return optionalCatalogue.map(this::mapToCatalogueResponse)
            .orElseThrow(() -> new RuntimeException("Catalogue not found with id: " + itemId));
}

    @Transactional
    public void deleteCatalogueById(Long itemId) {
        if (catalogueRepository.existsById(itemId)) {
            catalogueRepository.deleteById(itemId);
            log.info("Catalogue with id {} has been deleted", itemId);
        } else {
            throw new RuntimeException("Catalogue not found with id: " + itemId);
        }
    }

    @Transactional
    public void updateCatalogueById(Long itemId, CatalogueRequest catalogueRequest) {
        Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(itemId);
        if (optionalCatalogue.isPresent()) {
            Catalogue catalogue = optionalCatalogue.get();

            if (catalogueRequest.getItemName() != null) {
                catalogue.setItemName(catalogueRequest.getItemName());
            }
            if (catalogueRequest.getItemDescription() != null) {
                catalogue.setItemDescription(catalogueRequest.getItemDescription());
            }
            if (catalogueRequest.getVendorId() != null) {
                catalogue.setVendorId(catalogueRequest.getVendorId());
            }

            if (catalogueRequest.getPrice() != null) {
                catalogue.setPrice(catalogueRequest.getPrice());
            }
            catalogueRepository.save(catalogue);
            log.info("Catalogue with id {} has been updated", itemId);
        } else {
            throw new RuntimeException("Catalogue not found with id: " + itemId);
        }
    }
    @Transactional
    public void deleteAllCatalogues() {
        catalogueRepository.deleteAll();
        log.info("All catalogues have been deleted");
    }
    @Transactional
    public List<CatalogueResponse> isInCatalogue(List<String> itemName, Long vendorId) {
        log.info("Checking Catalogue");
        return catalogueRepository.findByItemNameInAndVendorId(itemName, vendorId).stream()
                .map(catalogue ->
                        CatalogueResponse.builder()
                                .itemId(catalogue.getItemId())
                                .itemName(catalogue.getItemName())
                                .itemDescription(catalogue.getItemDescription())
                                .vendorId(catalogue.getVendorId())
                                .price(catalogue.getPrice())
                                .build()
                ).toList();
    }
    private CatalogueResponse mapToCatalogueResponse(Catalogue catalogue) {
        return new CatalogueResponse(
                catalogue.getItemId(),
                catalogue.getItemName(),
                catalogue.getItemDescription(),
                catalogue.getVendorId(),
                catalogue.getPrice());
    }


}
