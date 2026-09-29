package com.example.garage.controller;

import com.example.garage.entity.Bill;
import com.example.garage.service.BillService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(
            BillService billService) {

        this.billService = billService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bill createBill(

            @Valid @RequestBody Bill bill,

            @RequestParam Long jobCardId) {

        return billService.createBill(
                bill,
                jobCardId
        );
    }

    @GetMapping
    public List<Bill> getAllBills() {

        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public Bill getBillById(
            @PathVariable Long id) {

        return billService.getBillById(id);
    }

    @PutMapping("/{id}")
    public Bill updateBill(
            @PathVariable Long id,
            @Valid @RequestBody Bill bill) {

        return billService.updateBill(
                id,
                bill
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(
            @PathVariable Long id) {

        billService.deleteBill(id);
    }
}