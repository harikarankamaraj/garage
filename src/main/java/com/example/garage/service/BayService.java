package com.example.garage.service;

import com.example.garage.entity.Bay;
import com.example.garage.exception.ResourceNotFoundException;
import com.example.garage.repository.BayRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BayService {

    private final BayRepository bayRepository;

    public BayService(BayRepository bayRepository) {
        this.bayRepository = bayRepository;
    }

    public Bay createBay(Bay bay) {
        return bayRepository.save(bay);
    }

    public List<Bay> getAllBays() {
        return bayRepository.findAll();
    }

    public Bay getBayById(Long id) {

        return bayRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bay not found with id: " + id
                        ));
    }

    public Bay updateBay(
            Long id,
            Bay bay) {

        Bay existingBay =
                getBayById(id);

        existingBay.setBayNumber(
                bay.getBayNumber()
        );

        existingBay.setLocation(
                bay.getLocation()
        );

        existingBay.setStatus(
                bay.getStatus()
        );

        return bayRepository.save(existingBay);
    }

    public void deleteBay(Long id) {

        Bay bay =
                getBayById(id);

        bayRepository.delete(bay);
    }
}