package com.skylark.business_intelligence_agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {

    private String id;
    private String dealName;
    private String ownerCode;
    private String clientCode;
    private String dealStatus;
    private String closeDate;
    private String closureProbability;
    private String dealValue;
    private String forecastDate;
    private String salesStage;
    private String productType;
    private String industry;
    private String lastUpdated;
}