package com.ziptruck.truckservice.controller;

import com.ziptruck.truckservice.dto.TruckRequest;
import com.ziptruck.truckservice.dto.TruckResponse;
import com.ziptruck.truckservice.service.TruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/truck")
@RequiredArgsConstructor
public class TruckController {

    private final TruckService truckService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newTruck(@RequestBody TruckRequest truckRequest){
        truckService.newTruck(truckRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TruckResponse> getAllTrucks(){
        return truckService.getAllTrucks();
    }
    @GetMapping("/{truckId}")
    @ResponseStatus(HttpStatus.OK)
    public TruckResponse getTruckById(@PathVariable Long truckId) {
        return truckService.getTruckById(truckId);
    }

    @DeleteMapping("/{truckId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTruckById(@PathVariable Long truckId) {
        truckService.deleteTruckById(truckId);
    }

    @PutMapping("/{truckId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTruckById(@PathVariable Long truckId, @RequestBody TruckRequest truckRequest) {
        truckService.updateTruckById(truckId, truckRequest);
    }
}
