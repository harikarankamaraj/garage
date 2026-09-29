package com.example.garage.service;

import com.example.garage.entity.Bill;
import com.example.garage.entity.JobCard;

import com.example.garage.exception.ResourceNotFoundException;

import com.example.garage.repository.BillRepository;
import com.example.garage.repository.JobCardRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final JobCardRepository jobCardRepository;

    public BillService(
            BillRepository billRepository,
            JobCardRepository jobCardRepository) {

        this.billRepository = billRepository;
        this.jobCardRepository = jobCardRepository;
    }

    public Bill createBill(
            Bill bill,
            Long jobCardId) {

        JobCard jobCard =
                jobCardRepository.findById(jobCardId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job card not found with id: "
                                                + jobCardId
                                ));

        bill.setJobCard(jobCard);

        double parts =
                bill.getPartsCost() != null
                        ? bill.getPartsCost()
                        : 0.0;

        double labour =
                bill.getLabourCost() != null
                        ? bill.getLabourCost()
                        : 0.0;

        bill.setTotalAmount(parts + labour);

        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {

        return billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found with id: " + id
                        ));
    }

    public Bill updateBill(
            Long id,
            Bill updatedBill) {

        Bill existingBill =
                getBillById(id);

        existingBill.setPartsCost(
                updatedBill.getPartsCost()
        );

        existingBill.setLabourCost(
                updatedBill.getLabourCost()
        );

        double parts =
                updatedBill.getPartsCost() != null
                        ? updatedBill.getPartsCost()
                        : 0.0;

        double labour =
                updatedBill.getLabourCost() != null
                        ? updatedBill.getLabourCost()
                        : 0.0;

        existingBill.setTotalAmount(
                parts + labour
        );

        return billRepository.save(existingBill);
    }

    public void deleteBill(Long id) {

        Bill bill =
                getBillById(id);

        billRepository.delete(bill);
    }
}