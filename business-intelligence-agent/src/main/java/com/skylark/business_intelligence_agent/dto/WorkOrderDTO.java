package com.skylark.business_intelligence_agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDTO {

    private String dealName;
    private String customerCode;
    private String serialNumber;
    private String natureOfWork;
    private String lastExecutedMonth;
    private String executionStatus;
    private String dataDeliveryDate;

}