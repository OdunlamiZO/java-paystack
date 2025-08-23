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
public class InitializeTransactionResponse {

    /** Unique reference for the transaction */
    private String reference;

    /** Authorization URL where the customer should be redirected */
    @JsonProperty("authorization_url")
    private String authorizationUrl;

    /** Access code for the transaction */
    @JsonProperty("access_code")
    private String accessCode;
}
