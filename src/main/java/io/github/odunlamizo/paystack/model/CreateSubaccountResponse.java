package io.github.odunlamizo.paystack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSubaccountResponse {
    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("percentage_charge")
    private Float percentageCharge;

    @JsonProperty("settlement_bank")
    private String settlementBank;

    private String currency;

    private Integer bank;

    private Integer integration;

    private String domain;

    @JsonProperty("account_name")
    private String accountName;

    private String product;

    @JsonProperty("managed_by_integration")
    private Integer managedByIntegration;

    @JsonProperty("subaccount_code")
    private String subaccountCode;

    @JsonProperty("is_verified")
    private boolean isVerified;

    @JsonProperty("settlement_schedule")
    private String settlementSchedule;

    private boolean active;

    private boolean migrate;

    private Long id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
