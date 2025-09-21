package io.github.odunlamizo.paystack.okhttp;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.odunlamizo.paystack.Paystack;
import io.github.odunlamizo.paystack.PaystackException;
import io.github.odunlamizo.paystack.model.*;
import io.github.odunlamizo.paystack.util.JsonUtil;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Consumer;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/** Paystack API implementation powered by OkHttp */
public class PaystackOkHttp implements Paystack {

    private final OkHttpClient client;
    private final String baseUrl;
    private final String secretKey;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public PaystackOkHttp(@NonNull String secretKey) {
        this(secretKey, "https://api.paystack.co");
    }

    public PaystackOkHttp(@NonNull String secretKey, @NonNull String baseUrl) {
        this.baseUrl = baseUrl;
        this.secretKey = secretKey;
        client = new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(secretKey)).build();
    }

    @Override
    public Response<AccountDetails> resolveAccount(
            @NonNull String accountNumber, @NonNull String bankCode) throws IOException {
        final String URL =
                String.format(
                        "%s/bank/resolve?account_number=%s&bank_code=%s",
                        baseUrl, accountNumber, bankCode);
        Request request = new Request.Builder().url(URL).build();

        return newCall(request, new TypeReference<>() {});
    }

    @Override
    public Response<List<Bank>> listBanks(@NonNull String country) throws IOException {
        final String URL = String.format("%s/bank?country=%s", baseUrl, country);
        Request request = new Request.Builder().url(URL).build();

        return newCall(request, new TypeReference<>() {});
    }

    @Override
    public Response<InitializeTransactionResponse> initializeTransaction(
            @NonNull InitializeTransactionRequest payload) throws IOException {
        final String URL = String.format("%s/transaction/initialize", baseUrl);
        Request request =
                new Request.Builder()
                        .url(URL)
                        .post(RequestBody.create(JsonUtil.toJson(payload), JSON))
                        .build();

        return newCall(request, new TypeReference<>() {});
    }

    @Override
    public <T> void processWebhook(
            @NonNull String payload, @NonNull String signature, @NonNull Consumer<String> handler)
            throws PaystackException, NoSuchAlgorithmException, InvalidKeyException {
        if (!isValidSignature(secretKey, payload, signature)) {
            throw new PaystackException("invalid webhook signature");
        }

        handler.accept(payload);
    }

    @Override
    public Response<CreateSubaccountResponse> createSubaccount(
            @NonNull CreateSubaccountRequest payload) throws IOException {
        final String URL = String.format("%s/subaccount", baseUrl);
        Request request =
                new Request.Builder()
                        .url(URL)
                        .post(RequestBody.create(JsonUtil.toJson(payload), JSON))
                        .build();

        return newCall(request, new TypeReference<>() {});
    }

    private <T> Response<T> newCall(Request request, TypeReference<Response<T>> typeRef)
            throws IOException {
        try (okhttp3.Response response = client.newCall(request).execute()) {

            String json = null;
            if (response.body() != null) {
                json = response.body().string();
            }

            return JsonUtil.toValue(json, typeRef).setCode(response.code());
        }
    }
}
