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
public class CreateRefundResponse {

    private Long id;

    private Long integration;

    private String domain;

    private Long transaction;

    private Long dispute;

    private Long amount;

    @JsonProperty("deducted_amount")
    private Long deductedAmount;

    private String currency;

    private String channel;

    @JsonProperty("fully_deducted")
    private boolean fullyDeducted;

    @JsonProperty("refunded_at")
    private String refundedAt;

    @JsonProperty("expected_at")
    private String expectedAt;

    @JsonProperty("customer_note")
    private String customerNote;

    @JsonProperty("merchant_note")
    private String merchantNote;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    private String status;
}
