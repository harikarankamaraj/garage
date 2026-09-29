package com.example.garage.controller;

import com.example.garage.entity.JobCard;
import com.example.garage.service.JobCardService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-cards")
public class JobCardController {

    private final JobCardService jobCardService;

    public JobCardController(
            JobCardService jobCardService) {

        this.jobCardService = jobCardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobCard createJobCard(

            @Valid @RequestBody JobCard jobCard,

            @RequestParam Long vehicleId,

            @RequestParam(required = false) Long bayId,

            @RequestParam(required = false) Long mechanicId) {

        return jobCardService.createJobCard(
                jobCard,
                vehicleId,
                bayId,
                mechanicId
        );
    }

    @GetMapping
    public List<JobCard> getAllJobCards() {

        return jobCardService.getAllJobCards();
    }

    @GetMapping("/{id}")
    public JobCard getJobCardById(
            @PathVariable Long id) {

        return jobCardService.getJobCardById(id);
    }

    @PutMapping("/{id}")
    public JobCard updateJobCard(

            @PathVariable Long id,

            @Valid @RequestBody JobCard jobCard,

            @RequestParam(required = false) Long vehicleId,

            @RequestParam(required = false) Long bayId,

            @RequestParam(required = false) Long mechanicId) {

        return jobCardService.updateJobCard(
                id,
                jobCard,
                vehicleId,
                bayId,
                mechanicId
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobCard(
            @PathVariable Long id) {

        jobCardService.deleteJobCard(id);
    }
}