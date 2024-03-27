package com.ziptruck.truckservice.service;

import com.ziptruck.truckservice.dto.TruckRequest;
import com.ziptruck.truckservice.dto.TruckResponse;
import com.ziptruck.truckservice.model.Truck;
import com.ziptruck.truckservice.repository.TruckRepository;
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
public class TruckService {

    private final TruckRepository truckRepository;
    public void newTruck(TruckRequest truckRequest){
            Truck truck = new Truck();
            truck.setLocation(truckRequest.getLocation());
            truck.setOrderId(truckRequest.getOrderId());
            truck.setCustomerId(truckRequest.getCustomerId());
            truck.setVendorId(truckRequest.getVendorId());

            truckRepository.save(truck);
            log.info("Truck {} is saved", truck.getTruckId());
        }


        public List<TruckResponse> getAllTrucks() {
        List<Truck> truck = truckRepository.findAll();

            return truck.stream()
                    .map(this::mapToTruckResponse)
                    .collect(Collectors.toList());
    }

    public TruckResponse getTruckById(Long truckId) {
        Optional<Truck> optionalTruck = truckRepository.findById(truckId);
        if (optionalTruck.isPresent()) {
            return mapToTruckResponse(optionalTruck.get());
        } else {
            throw new RuntimeException("Truck not found with id: " + truckId);
        }
    }

    @Transactional
    public void deleteTruckById(Long truckId) {
        if (truckRepository.existsById(truckId)) {
            truckRepository.deleteById(truckId);
            log.info("Truck with id {} has been deleted", truckId);
        } else {
            throw new RuntimeException("Truck not found with id: " + truckId);
        }
    }

    @Transactional
    public void updateTruckById(Long truckId, TruckRequest truckRequest) {
        Optional<Truck> optionalTruck = truckRepository.findById(truckId);
        if (optionalTruck.isPresent()) {
            Truck truck = optionalTruck.get();

            if (truckRequest.getLocation() != null) {
                truck.setLocation(truckRequest.getLocation());
            }

            if (truckRequest.getOrderId() != null) {
                truck.setOrderId(truckRequest.getOrderId());
            }

            if (truckRequest.getCustomerId() != null) {
                truck.setCustomerId(truckRequest.getCustomerId());
            }

            if (truckRequest.getVendorId() != null) {
                truck.setVendorId(truckRequest.getVendorId());
            }

            truckRepository.save(truck);
            log.info("Truck with id {} has been updated", truckId);
        } else {
            throw new RuntimeException("Truck not found with id: " + truckId);
        }
    }

    private TruckResponse mapToTruckResponse(Truck truck) {
        return new TruckResponse(
                truck.getTruckId(),
                truck.getLocation(),
                truck.getOrderId(),
                truck.getCustomerId(),
                truck.getVendorId()
        );
    }
}
