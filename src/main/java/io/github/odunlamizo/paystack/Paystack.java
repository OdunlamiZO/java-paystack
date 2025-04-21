package io.github.odunlamizo.paystack;

import jakarta.validation.constraints.NotNull;
import java.util.List;

import io.github.odunlamizo.paystack.model.AccountDetails;
import io.github.odunlamizo.paystack.model.Bank;
import io.github.odunlamizo.paystack.model.Response;

/** Paystack API */
public interface Paystack {

    /**
     * Confirm an account belongs to the right customer
     *
     * @param accountNumber - customer's account number
     * @param bankCode - customer's bank code
     * @return Response<AccountDetails> - response containing customer's account details
     * @throws PaystackException
     */
    Response<AccountDetails> resolveAccount(@NotNull String accountNumber, @NotNull String bankCode)
            throws PaystackException;

    /**
     * Get a list of all supported banks and their properties.
     *
     * @param country - The country from which to obtain the list of supported banks. Accepted
     *     values are: ghana, kenya, nigeria, south africa
     * @return Response<List<Bank>> - response containing list of supported banks
     * @throws PaystackException
     */
    Response<List<Bank>> listBanks(String country) throws PaystackException;
}
