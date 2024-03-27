package com.ziptruck.catalogueservice.controller;

import com.ziptruck.catalogueservice.dto.CatalogueRequest;
import com.ziptruck.catalogueservice.dto.CatalogueResponse;
import com.ziptruck.catalogueservice.service.CatalogueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/catalogue")
@RequiredArgsConstructor
public class CatalogueController {

    private final CatalogueService catalogueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCatalogue(@RequestBody CatalogueRequest catalogueRequest) {
        catalogueService.createCatalogue(catalogueRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatalogueResponse> getAllCatalogue() {

        return catalogueService.getAllCatalogue();
    }
    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public CatalogueResponse getCatalogueById(@PathVariable Long itemId) {

        return catalogueService.getCatalogueById(itemId);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatalogueResponse> isInCatalogue(@RequestParam List<String> itemNames, @RequestParam Long vendorId) {
        return catalogueService.isInCatalogue(itemNames, vendorId);
    }
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatalogueById(@PathVariable Long itemId) {
        catalogueService.deleteCatalogueById(itemId);
    }

    @PutMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCatalogueById(@PathVariable Long itemId, @RequestBody CatalogueRequest catalogueRequest) {
        catalogueService.updateCatalogueById(itemId, catalogueRequest);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCatalogues() {
        catalogueService.deleteAllCatalogues();
    }
}
