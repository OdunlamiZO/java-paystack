package io.github.odunlamizo.paystack;

import io.github.odunlamizo.paystack.model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Consumer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.NonNull;

/** Paystack API */
public interface Paystack {

    /**
     * Verifies that an account belongs to the specified customer.
     *
     * @param accountNumber the customer's account number
     * @param bankCode the customer's bank code
     * @return a {@link Response} containing the {@link AccountDetails} of the account
     * @throws IOException if a network or I/O error occurs while making the request
     */
    Response<AccountDetails> resolveAccount(@NonNull String accountNumber, @NonNull String bankCode)
            throws IOException;

    /**
     * Retrieves the list of supported banks for a given country.
     *
     * @param country the country to fetch banks for (e.g. "ghana", "kenya", "nigeria", "south
     *     africa")
     * @return a {@link Response} containing the list of supported {@link Bank} objects
     * @throws IOException if a network or I/O error occurs while making the request
     */
    Response<List<Bank>> listBanks(@NonNull String country) throws IOException;

    /**
     * Initializes a Paystack transaction for a customer.
     *
     * <p>This method creates a new transaction on Paystack and returns an authorization URL and
     * related metadata that can be used to complete payment.
     *
     * @param payload the transaction initialization payload
     * @return a {@link Response} containing the Paystack initialization response
     * @throws IOException if a network or I/O error occurs while making the request
     */
    Response<InitializeTransactionResponse> initializeTransaction(
            @NonNull InitializeTransactionRequest payload) throws IOException;

    /**
     * Validates and processes a Paystack webhook event.
     *
     * <p>This method verifies the integrity of the incoming webhook request using the configured
     * secret key and the HMAC-SHA512 signature provided by Paystack. If the signature is valid, the
     * payload is deserialized into a {@link PaystackEvent} object and passed to the supplied
     * handler for further processing (e.g., updating transaction or subscription status).
     *
     * @param payload the raw JSON payload received from Paystack
     * @param signature the HMAC-SHA512 signature from the webhook request headers
     * @param handler a consumer function that processes the validated payload
     * @throws PaystackException if the signature is invalid or another SDK-related error occurs
     * @throws NoSuchAlgorithmException if the HMAC-SHA512 algorithm is unavailable in the runtime
     * @throws InvalidKeyException if the secret key used for signature validation is invalid
     */
    void processWebhook(
            @NonNull String payload, @NonNull String signature, @NonNull Consumer<String> handler)
            throws PaystackException, NoSuchAlgorithmException, InvalidKeyException;

    /**
     * Creates a new subaccount on Paystack.
     *
     * <p>This method registers a subaccount under your Paystack account, which can be used to
     * receive payments separately from your main account.
     *
     * @param payload the subaccount creation payload
     * @return a {@link Response} containing the Paystack subaccount creation response
     * @throws IOException if a network or I/O error occurs while making the request
     */
    Response<CreateSubaccountResponse> createSubaccount(@NonNull CreateSubaccountRequest payload)
            throws IOException;

    default boolean isValidSignature(String secretKey, String body, String signature)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha512 = Mac.getInstance("HmacSHA512");
        sha512.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
        String hash = bytesToHex(sha512.doFinal(body.getBytes(StandardCharsets.UTF_8)));
        return hash.equals(signature);
    }

    default String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
