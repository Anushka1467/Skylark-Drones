 package com.skylark.business_intelligence_agent.controller;

import com.skylark.business_intelligence_agent.dto.DealDTO;
import com.skylark.business_intelligence_agent.dto.WorkOrderDTO;
import com.skylark.business_intelligence_agent.service.BusinessInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private BusinessInsightService businessInsightService;

    @GetMapping("/deals")
    public List<DealDTO> getDeals() {
        return businessInsightService.getDealsData();
    }

    @GetMapping("/workorders")
    public List<WorkOrderDTO> getWorkOrders() {
        return businessInsightService.getWorkOrdersData();
    }

    @GetMapping("/all")
    public String getAllData() {
        return businessInsightService.getAllBusinessData();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> request) {

        String question = request.get("question");

        return businessInsightService.askQuestion(question);
    }
}