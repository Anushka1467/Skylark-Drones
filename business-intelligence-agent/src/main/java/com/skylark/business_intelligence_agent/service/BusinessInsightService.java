package com.skylark.business_intelligence_agent.service;

import com.skylark.business_intelligence_agent.dto.DealDTO;
import com.skylark.business_intelligence_agent.dto.WorkOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessInsightService {

    @Autowired
    private MondayService mondayService;

    @Autowired
    private GroqService groqService;

    public List<DealDTO> getDealsData() {
        return mondayService.getDeals();
    }

    public List<WorkOrderDTO> getWorkOrdersData() {
        return mondayService.getWorkOrders();
    }

    public String askQuestion(String question) {

        // Fetch all data
        List<DealDTO> deals = mondayService.getDeals();
        List<WorkOrderDTO> workOrders = mondayService.getWorkOrders();

        String q = question.toLowerCase();

        // Highest Closure Probability
        if (q.contains("closure probability")
                || q.contains("highest probability")
                || q.contains("high probability")) {

            deals = deals.stream()
                    .filter(d -> "High".equalsIgnoreCase(d.getClosureProbability()))
                    .toList();

            workOrders = List.of();
        }

        // Work orders requiring attention
        else if (q.contains("delayed")
                || q.contains("pending")
                || q.contains("ongoing")
                || q.contains("not started")) {

            workOrders = workOrders.stream()
                    .filter(w ->
                            "Ongoing".equalsIgnoreCase(w.getExecutionStatus())
                                    || "Not Started".equalsIgnoreCase(w.getExecutionStatus())
                                    || "Pause / struck".equalsIgnoreCase(w.getExecutionStatus())
                                    || "Details pending from Client".equalsIgnoreCase(w.getExecutionStatus())
                    )
                    .toList();

            deals = List.of();
        }

        // Default
        else {

            deals = deals.stream()
                    .limit(5)
                    .toList();

            workOrders = workOrders.stream()
                    .limit(5)
                    .toList();
        }

        StringBuilder dealData = new StringBuilder();

        for (DealDTO deal : deals) {

            dealData.append("""
                    -------------------------
                    Deal Name: %s
                    Deal Status: %s
                    Deal Value: %s
                    Closure Probability: %s
                    Sales Stage: %s
                    Product Type: %s
                    Industry: %s

                    """.formatted(
                    deal.getDealName(),
                    deal.getDealStatus(),
                    deal.getDealValue(),
                    deal.getClosureProbability(),
                    deal.getSalesStage(),
                    deal.getProductType(),
                    deal.getIndustry()
            ));
        }

        StringBuilder workOrderData = new StringBuilder();

        for (WorkOrderDTO workOrder : workOrders) {

            workOrderData.append("""
                    -------------------------
                    Deal Name: %s
                    Customer Code: %s
                    Execution Status: %s
                    Nature Of Work: %s
                    Last Executed Month: %s

                    """.formatted(
                    workOrder.getDealName(),
                    workOrder.getCustomerCode(),
                    workOrder.getExecutionStatus(),
                    workOrder.getNatureOfWork(),
                    workOrder.getLastExecutedMonth()
            ));
        }

        String prompt = """
                You are the Business Intelligence Assistant for Skylark Drones.

                Your job is to answer founder-level business questions using ONLY the business data provided.

                Rules:
                - Never invent information.
                - Never use outside knowledge.
                - Never ask the user to rephrase unless the question is unrelated.
                - Base every answer ONLY on the supplied business data.
                - If no matching records exist, clearly mention that.
                - Keep answers concise and professional.

                Respond exactly in this format:

                Summary:
                ...

                Key Findings:
                ...

                Business Impact:
                ...

                Recommendations:
                ...

                Business Data

                Deals:
                %s

                Work Orders:
                %s

                User Question:
                %s
                """.formatted(
                dealData.toString(),
                workOrderData.toString(),
                question
        );

        return groqService.askGroq(prompt);
    }

    public String getAllBusinessData() {
        return "Not implemented yet";
    }
}