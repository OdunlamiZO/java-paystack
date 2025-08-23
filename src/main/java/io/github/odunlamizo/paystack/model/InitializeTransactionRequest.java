package io.github.odunlamizo.paystack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class InitializeTransactionRequest {

    /** Amount to charge, in the subunit of the currency (e.g., kobo for NGN). */
    private String amount;

    /** Customer's email address. */
    private String email;

    /** Optional. Transaction currency. Defaults to your integration currency. */
    private String currency;

    /** Optional. Unique transaction reference. Only -, ., = and alphanumeric allowed. */
    private String reference;

    /** Optional. Callback URL to override the default dashboard URL for this transaction. */
    @JsonProperty("callback_url")
    private String callbackUrl;

    /** Optional. Plan code for subscription transactions; overrides the amount if provided. */
    private String plan;

    /** Optional. Number of times to charge the customer during a subscription. */
    @JsonProperty("invoice_limit")
    private Integer invoiceLimit;

    /** Optional. Custom metadata as a stringified JSON object. */
    private String metadata;

    /** Optional. Payment channels allowed for this transaction (e.g., "card", "bank", "ussd"). */
    private List<String> channels;

    /** Optional. Code for splitting the transaction payment. */
    @JsonProperty("split_code")
    private String splitCode;

    /** Optional. Subaccount code that will own this payment. */
    private String subaccount;

    /** Optional. Amount to override the default split configuration for this transaction. */
    @JsonProperty("transaction_charge")
    private Integer transactionCharge;

    /**
     * Optional. Indicates who bears the transaction charges ("account" or "subaccount"). Defaults
     * to "account".
     */
    private String bearer;
}
