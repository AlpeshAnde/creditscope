package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.RepaymentHistory;
import com.alpesh.creditscope.service.RepaymentHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repaymentHistory")
public class RepaymentHistoryController {
    private final RepaymentHistoryService repaymentHistoryService;

    public RepaymentHistoryController(RepaymentHistoryService repaymentHistoryService) {
        this.repaymentHistoryService = repaymentHistoryService;
    }
    //Get All History
    @GetMapping
    public List<RepaymentHistory> getAllRepaymentHistories()
    {
        return repaymentHistoryService.getAllRepaymentHistories();
    }
   //Get History by id
    @GetMapping("/{id}")
    public  RepaymentHistory getRepaymentHistoryById(@PathVariable long id)
    {
        return repaymentHistoryService.getRepaymentHistoryById(id);
    }
    //Create repayment History
    @PostMapping
    public RepaymentHistory createRepaymentHistory(@RequestBody RepaymentHistory repaymentHistory){
        return repaymentHistoryService.createRepaymentHistory(repaymentHistory);
    }

}
