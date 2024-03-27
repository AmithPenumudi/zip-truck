package com.ziptruck.truckservice.service;

import com.ziptruck.truckservice.dto.TruckRequest;
import com.ziptruck.truckservice.dto.TruckResponse;
import com.ziptruck.truckservice.model.Truck;
import com.ziptruck.truckservice.model.orderList;
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
            truck.setLocationList(truckRequest.getLocationList());
            truck.setOrderIdList(truckRequest.getOrderIdList());
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
    @Transactional
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

            if (truckRequest.getLocationList() != null) {
                truck.setLocationList(truckRequest.getLocationList());
            }

            if (truckRequest.getOrderIdList() != null) {
                truck.setOrderIdList(truckRequest.getOrderIdList());
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
    @Transactional
    public void updateOrderList(Long truckId, Long orderId){
        Optional<Truck> optionalTruck = truckRepository.findById(truckId);
        if (optionalTruck.isPresent()) {
            Truck truck = optionalTruck.get();
            List<orderList> orderList = truck.getOrderIdList();
            orderList.add(new orderList(orderId));
            orderList.add((orderList) orderList);
            truck.setOrderIdList(orderList);
            truckRepository.save(truck);
            log.info("Order with id {} has been added to truck with id {}", orderId, truckId);
        } else {
            throw new RuntimeException("Truck not found with id: " + truckId);
        }


    }
    private TruckResponse mapToTruckResponse(Truck truck) {
        return new TruckResponse(
                truck.getTruckId(),
                truck.getLocationList(),
                truck.getOrderIdList(),
                truck.getVendorId()
        );
    }
    @Transactional
    public TruckResponse getOrderIdsByTruckId(Long truckId) {
        Optional<Truck> optionalTruck = truckRepository.findById(truckId);
        if (optionalTruck.isPresent()) {
            Truck truck = optionalTruck.get();
            return new TruckResponse(
                    truck.getTruckId(),
                    truck.getLocationList(),
                    truck.getOrderIdList(),
                    truck.getVendorId()
            );
        } else {
            throw new RuntimeException("Truck not found with id: " + truckId);
        }
    }
}
