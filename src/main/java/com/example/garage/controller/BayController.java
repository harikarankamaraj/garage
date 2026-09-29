package com.example.garage.controller;

import com.example.garage.entity.Bay;
import com.example.garage.service.BayService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bays")
public class BayController {

    private final BayService bayService;

    public BayController(BayService bayService) {
        this.bayService = bayService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bay createBay(
            @Valid @RequestBody Bay bay) {

        return bayService.createBay(bay);
    }

    @GetMapping
    public List<Bay> getAllBays() {

        return bayService.getAllBays();
    }

    @GetMapping("/{id}")
    public Bay getBayById(
            @PathVariable Long id) {

        return bayService.getBayById(id);
    }

    @PutMapping("/{id}")
    public Bay updateBay(
            @PathVariable Long id,
            @Valid @RequestBody Bay bay) {

        return bayService.updateBay(
                id,
                bay
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBay(
            @PathVariable Long id) {

        bayService.deleteBay(id);
    }
}