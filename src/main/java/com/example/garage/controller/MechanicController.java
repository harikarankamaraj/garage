package com.example.garage.controller;

import com.example.garage.entity.Mechanic;
import com.example.garage.service.MechanicService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(
            MechanicService mechanicService) {

        this.mechanicService = mechanicService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mechanic createMechanic(
            @Valid @RequestBody Mechanic mechanic) {

        return mechanicService.createMechanic(
                mechanic
        );
    }

    @GetMapping
    public List<Mechanic> getAllMechanics() {

        return mechanicService.getAllMechanics();
    }

    @GetMapping("/{id}")
    public Mechanic getMechanicById(
            @PathVariable Long id) {

        return mechanicService.getMechanicById(id);
    }

    @PutMapping("/{id}")
    public Mechanic updateMechanic(
            @PathVariable Long id,
            @Valid @RequestBody Mechanic mechanic) {

        return mechanicService.updateMechanic(
                id,
                mechanic
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMechanic(
            @PathVariable Long id) {

        mechanicService.deleteMechanic(id);
    }
}