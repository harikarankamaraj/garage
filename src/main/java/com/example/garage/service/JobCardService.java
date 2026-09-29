package com.example.garage.service;

import com.example.garage.entity.Bay;
import com.example.garage.entity.JobCard;
import com.example.garage.entity.Mechanic;
import com.example.garage.entity.Vehicle;

import com.example.garage.exception.BusinessException;
import com.example.garage.exception.ResourceNotFoundException;

import com.example.garage.repository.BayRepository;
import com.example.garage.repository.JobCardRepository;
import com.example.garage.repository.MechanicRepository;
import com.example.garage.repository.VehicleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class JobCardService {

    private final JobCardRepository jobCardRepository;
    private final VehicleRepository vehicleRepository;
    private final BayRepository bayRepository;
    private final MechanicRepository mechanicRepository;

    public JobCardService(
            JobCardRepository jobCardRepository,
            VehicleRepository vehicleRepository,
            BayRepository bayRepository,
            MechanicRepository mechanicRepository) {

        this.jobCardRepository = jobCardRepository;
        this.vehicleRepository = vehicleRepository;
        this.bayRepository = bayRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @Transactional
    public JobCard createJobCard(
            JobCard jobCard,
            Long vehicleId,
            Long bayId,
            Long mechanicId) {

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Vehicle not found with id: "
                                                + vehicleId
                                ));

        Bay bay = null;

        if (bayId != null) {

            bay = bayRepository.findById(bayId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Bay not found with id: "
                                            + bayId
                            ));

            checkBayAvailability(bayId);
        }

        Mechanic mechanic = null;

        if (mechanicId != null) {

            mechanic =
                    mechanicRepository.findById(mechanicId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Mechanic not found with id: "
                                                    + mechanicId
                                    ));
        }

        jobCard.setVehicle(vehicle);
        jobCard.setBay(bay);
        jobCard.setMechanic(mechanic);

        if (jobCard.getStatus() == null ||
                jobCard.getStatus().isBlank()) {

            jobCard.setStatus("WAITING");
        }

        return jobCardRepository.save(jobCard);
    }

    private void checkBayAvailability(Long bayId) {

        List<String> activeStatuses = Arrays.asList(
                "WAITING",
                "IN_PROGRESS",
                "QUALITY_CHECK"
        );

        boolean occupied =
                jobCardRepository.existsByBayIdAndStatusIn(
                        bayId,
                        activeStatuses
                );

        if (occupied) {

            throw new BusinessException(
                    "Bay " + bayId +
                            " is already occupied by an unfinished job."
            );
        }
    }

    public List<JobCard> getAllJobCards() {
        return jobCardRepository.findAll();
    }

    public JobCard getJobCardById(Long id) {

        return jobCardRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job card not found with id: " + id
                        ));
    }

    @Transactional
    public JobCard updateJobCard(
            Long id,
            JobCard updatedJobCard,
            Long vehicleId,
            Long bayId,
            Long mechanicId) {

        JobCard existingJobCard =
                getJobCardById(id);

        if (vehicleId != null) {

            Vehicle vehicle =
                    vehicleRepository.findById(vehicleId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Vehicle not found with id: "
                                                    + vehicleId
                                    ));

            existingJobCard.setVehicle(vehicle);
        }

        if (bayId != null) {

            Long existingBayId =
                    existingJobCard.getBay() != null
                            ? existingJobCard.getBay().getId()
                            : null;

            if (!bayId.equals(existingBayId)) {
                checkBayAvailability(bayId);
            }

            Bay bay =
                    bayRepository.findById(bayId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Bay not found with id: "
                                                    + bayId
                                    ));

            existingJobCard.setBay(bay);
        }

        if (mechanicId != null) {

            Mechanic mechanic =
                    mechanicRepository.findById(mechanicId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Mechanic not found with id: "
                                                    + mechanicId
                                    ));

            existingJobCard.setMechanic(mechanic);
        }

        if (updatedJobCard.getRequestedServices() != null) {

            existingJobCard.setRequestedServices(
                    updatedJobCard.getRequestedServices()
            );
        }

        if (updatedJobCard.getStatus() != null) {

            validateStatusChange(
                    existingJobCard.getStatus(),
                    updatedJobCard.getStatus()
            );

            existingJobCard.setStatus(
                    updatedJobCard.getStatus()
            );
        }

        return jobCardRepository.save(existingJobCard);
    }

    private void validateStatusChange(
            String currentStatus,
            String newStatus) {

        if ("COMPLETED".equalsIgnoreCase(newStatus)
                && !"QUALITY_CHECK".equalsIgnoreCase(currentStatus)) {

            throw new BusinessException(
                    "A job card cannot be marked COMPLETED " +
                            "until it has passed QUALITY_CHECK."
            );
        }
    }

    public void deleteJobCard(Long id) {

        JobCard jobCard =
                getJobCardById(id);

        jobCardRepository.delete(jobCard);
    }
}