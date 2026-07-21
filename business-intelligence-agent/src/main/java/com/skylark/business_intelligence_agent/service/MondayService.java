package com.skylark.business_intelligence_agent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skylark.business_intelligence_agent.dto.DealDTO;
import com.skylark.business_intelligence_agent.dto.WorkOrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MondayService {

    @Value("${monday.api.url}")
    private String apiUrl;

    @Value("${monday.api.token}")
    private String apiToken;

    @Value("${monday.deals.board.id}")
    private String dealsBoardId;

    @Value("${monday.workorders.board.id}")
    private String workOrdersBoardId;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<DealDTO> getDeals() {
        String json = fetchBoardData(dealsBoardId);
        return parseDeals(json);
    }

    public List<WorkOrderDTO> getWorkOrders() {
        String json = fetchBoardData(workOrdersBoardId);
        return parseWorkOrders(json);
    }

    private String fetchBoardData(String boardId) {

    String graphql = String.format(
            "query { boards(ids:%s) { items_page(limit:500) { items { id name column_values { id text value } } } } }",
            boardId
    );

    String requestBody = "{\"query\":\"" + graphql + "\"}";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", apiToken);

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            entity,
            String.class
    );

    return response.getBody();
    }   
    private List<DealDTO> parseDeals(String json) {

        List<DealDTO> deals = new ArrayList<>();

        try {

            JsonNode root = objectMapper.readTree(json);

            JsonNode items = root
                    .path("data")
                    .path("boards")
                    .get(0)
                    .path("items_page")
                    .path("items");

            for (JsonNode item : items) {

                DealDTO deal = new DealDTO();

                deal.setId(safeText(item.path("id")));
                deal.setDealName(safeText(item.path("name")));

                JsonNode columnValues = item.path("column_values");

                deal.setOwnerCode(getColumnText(columnValues, 0));
                deal.setClientCode(getColumnText(columnValues, 1));
                deal.setDealStatus(getColumnText(columnValues, 2));
                deal.setCloseDate(getColumnText(columnValues, 3));
                deal.setClosureProbability(getColumnText(columnValues, 4));
                deal.setDealValue(getColumnText(columnValues, 5));
                deal.setForecastDate(getColumnText(columnValues, 6));
                deal.setSalesStage(getColumnText(columnValues, 7));
                deal.setProductType(getColumnText(columnValues, 8));
                deal.setIndustry(getColumnText(columnValues, 9));
                deal.setLastUpdated(getColumnText(columnValues, 10));

                deals.add(deal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return deals;
    }

    private String getColumnText(JsonNode columnValues, int index) {

        if (columnValues == null || index >= columnValues.size()) {
            return "";
        }

        JsonNode textNode = columnValues.get(index).path("text");

        if (textNode.isMissingNode() || textNode.isNull()) {
            return "";
        }

        return textNode.asText();
    }

    private String safeText(JsonNode node) {

        if (node == null || node.isNull()) {
            return "";
        }

        return node.asText();
    }
        private List<WorkOrderDTO> parseWorkOrders(String json) {

        List<WorkOrderDTO> workOrders = new ArrayList<>();

        try {

            JsonNode root = objectMapper.readTree(json);

            JsonNode items = root
                    .path("data")
                    .path("boards")
                    .get(0)
                    .path("items_page")
                    .path("items");

            for (JsonNode item : items) {

                WorkOrderDTO workOrder = new WorkOrderDTO();

                workOrder.setDealName(safeText(item.path("name")));

                JsonNode columnValues = item.path("column_values");

                workOrder.setCustomerCode(getColumnText(columnValues, 0));
                workOrder.setSerialNumber(getColumnText(columnValues, 1));
                workOrder.setNatureOfWork(getColumnText(columnValues, 2));
                workOrder.setLastExecutedMonth(getColumnText(columnValues, 3));
                workOrder.setExecutionStatus(getColumnText(columnValues, 4));
                workOrder.setDataDeliveryDate(getColumnText(columnValues, 6));

                workOrders.add(workOrder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return workOrders;
    }
}