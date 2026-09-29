package com.example.garage.service;

import com.example.garage.entity.Mechanic;
import com.example.garage.exception.ResourceNotFoundException;
import com.example.garage.repository.MechanicRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public Mechanic createMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic getMechanicById(Long id) {

        return mechanicRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mechanic not found with id: " + id
                        ));
    }

    public Mechanic updateMechanic(
            Long id,
            Mechanic mechanic) {

        Mechanic existingMechanic =
                getMechanicById(id);

        existingMechanic.setName(
                mechanic.getName()
        );

        existingMechanic.setPhoneNumber(
                mechanic.getPhoneNumber()
        );

        existingMechanic.setSpecialization(
                mechanic.getSpecialization()
        );

        existingMechanic.setStatus(
                mechanic.getStatus()
        );

        return mechanicRepository.save(existingMechanic);
    }

    public void deleteMechanic(Long id) {

        Mechanic mechanic =
                getMechanicById(id);

        mechanicRepository.delete(mechanic);
    }
}