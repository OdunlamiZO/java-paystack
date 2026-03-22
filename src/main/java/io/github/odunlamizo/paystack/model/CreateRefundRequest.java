package io.github.odunlamizo.paystack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRefundRequest {

    /** Transaction reference or id. */
    private String transaction;

    /**
     * Optional. Amount (in kobo for NGN, pesewas for GHS, cents for ZAR) to refund. Defaults to
     * full amount.
     */
    private Long amount;

    /** Optional. Three-letter ISO currency. */
    private String currency;

    /** Optional. Customer reason for refund (max 25 characters). */
    @JsonProperty("customer_note")
    private String customerNote;

    /** Optional. Merchant reason for refund (max 25 characters). */
    @JsonProperty("merchant_note")
    private String merchantNote;
}
