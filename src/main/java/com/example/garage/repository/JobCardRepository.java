package com.example.garage.repository;

import com.example.garage.entity.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    Optional<JobCard> findByJobCardNumber(String jobCardNumber);

    boolean existsByBayIdAndStatusIn(
            Long bayId,
            Collection<String> statuses
    );
}