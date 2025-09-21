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
public class CreateSubaccountRequest {

    /** Name of business for subaccount. */
    @JsonProperty("business_name")
    private String businessName;

    /** Bank code for the bank. */
    @JsonProperty("bank_code")
    private String bankCode;

    /** Bank account number. */
    @JsonProperty("account_number")
    private String accountNumber;

    /** Percentage the main account receives from each payment made to the subaccount. */
    @JsonProperty("percentage_charge")
    private Float percentageCharge;

    /** Optional. A description for this subaccount. */
    private String description;

    /** Optional. A contact email for the subaccount. */
    @JsonProperty("primary_contact_email")
    private String primaryContactEmail;

    /** Optional. A name for the contact person for this subaccount. */
    @JsonProperty("primary_contact_name")
    private String primaryContactName;

    /** Optional. A phone number to call for this subaccount. */
    @JsonProperty("primary_contact_phone")
    private String primaryContactPhone;

    /**
     * Optional. Stringified JSON object.
     * Example: {"custom_fields":[{"display_name":"Cart ID","variable_name":"cart_id","value":"8393"}]}
     */
    private String metadata;
}
