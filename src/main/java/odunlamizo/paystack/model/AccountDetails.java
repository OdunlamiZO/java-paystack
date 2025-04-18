package odunlamizo.paystack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("bank_id")
    private int bankId;
}
