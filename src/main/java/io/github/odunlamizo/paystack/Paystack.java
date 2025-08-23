package io.github.odunlamizo.paystack;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.odunlamizo.paystack.model.*;
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
     * @throws PaystackException if the request fails due to network issues or API errors
     */
    Response<AccountDetails> resolveAccount(@NonNull String accountNumber, @NonNull String bankCode)
            throws PaystackException;

    /**
     * Retrieves the list of supported banks for a given country.
     *
     * @param country the country to fetch banks for (e.g. "ghana", "kenya", "nigeria", "south
     *     africa")
     * @return a {@link Response} containing the list of supported {@link Bank} objects
     * @throws PaystackException if the request fails due to network issues or API errors
     */
    Response<List<Bank>> listBanks(@NonNull String country) throws PaystackException;

    /**
     * Initializes a Paystack transaction for a customer.
     *
     * <p>This method creates a new transaction on Paystack and returns an authorization URL and
     * related metadata that can be used to complete payment.
     *
     * @param request the transaction initialization payload
     * @return a {@link Response} containing the Paystack initialization response
     * @throws PaystackException if the request fails due to network errors, invalid parameters, or
     *     Paystack API errors
     */
    Response<InitializeTransactionResponse> initializeTransaction(
            @NonNull InitializeTransactionRequest request)
            throws PaystackException, JsonProcessingException;

    /**
     * Processes a Paystack webhook payload by validating its integrity and forwarding the event to
     * the provided handler function.
     *
     * <p>The method performs signature verification using the configured secret key to ensure that
     * the webhook payload originates from Paystack. If validation succeeds, the payload is
     * deserialized into a {@link PaystackEvent} object and passed to the supplied handler for
     * further processing.
     *
     * @param payload the raw JSON payload received from Paystack
     * @param signature the HMAC-SHA512 signature from the webhook request headers
     * @param handler a consumer function that handles the validated {@link PaystackEvent} instance
     *     (e.g., updating transaction status in your system)
     * @param <T> the type of event data contained in the webhook (e.g., transaction, subscription)
     * @throws PaystackException if payload validation or processing fails due to an SDK-related
     *     error
     * @throws NoSuchAlgorithmException if the HMAC-SHA512 algorithm is not available in the runtime
     * @throws InvalidKeyException if the secret key used for validation is invalid
     * @throws JsonProcessingException if deserialization of the webhook payload into {@link
     *     PaystackEvent} fails
     */
    <T> void processWebhook(
            @NonNull String payload,
            @NonNull String signature,
            @NonNull Consumer<PaystackEvent<T>> handler)
            throws PaystackException,
                    NoSuchAlgorithmException,
                    InvalidKeyException,
                    JsonProcessingException;

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
