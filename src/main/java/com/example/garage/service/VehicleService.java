package com.example.garage.service;

import com.example.garage.entity.Vehicle;
import com.example.garage.exception.ResourceNotFoundException;
import com.example.garage.repository.VehicleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id: " + id
                        ));
    }

    public Vehicle updateVehicle(
            Long id,
            Vehicle vehicle) {

        Vehicle existingVehicle =
                getVehicleById(id);

        existingVehicle.setRegistrationNumber(
                vehicle.getRegistrationNumber()
        );

        existingVehicle.setOwnerName(
                vehicle.getOwnerName()
        );

        existingVehicle.setPhoneNumber(
                vehicle.getPhoneNumber()
        );

        existingVehicle.setVehicleModel(
                vehicle.getVehicleModel()
        );

        existingVehicle.setVehicleBrand(
                vehicle.getVehicleBrand()
        );

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {

        Vehicle vehicle =
                getVehicleById(id);

        vehicleRepository.delete(vehicle);
    }
}