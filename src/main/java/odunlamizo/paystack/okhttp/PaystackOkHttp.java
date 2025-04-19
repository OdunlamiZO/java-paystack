package odunlamizo.paystack.okhttp;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import odunlamizo.paystack.Paystack;
import odunlamizo.paystack.PaystackException;
import odunlamizo.paystack.model.AccountDetails;
import odunlamizo.paystack.model.Bank;
import odunlamizo.paystack.model.Response;
import odunlamizo.paystack.util.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/** Paystack API implementation powered by OkHttp */
public class PaystackOkHttp implements Paystack {

    private final OkHttpClient client;
    private final String baseUrl;

    public PaystackOkHttp(@NotNull String secretKey) {
        this(secretKey, "https://api.paystack.co");
    }

    public PaystackOkHttp(@NotNull String secretKey, @NotNull String baseUrl) {
        this.baseUrl = baseUrl;
        client = new OkHttpClient.Builder().addInterceptor(new AuthInterceptor(secretKey)).build();
    }

    @Override
    public Response<AccountDetails> resolveAccount(
            @NotNull String accountNumber, @NotNull String bankCode) throws PaystackException {
        final String URL =
                String.format(
                        "%s/bank/resolve?account_number=%s&bank_code=%s",
                        baseUrl, accountNumber, bankCode);
        Request request = new Request.Builder().url(URL).build();

        return newCall(request, new TypeReference<Response<AccountDetails>>() {});
    }

    @Override
    public Response<List<Bank>> listBanks(@NotNull String country) throws PaystackException {
        final String URL = String.format("%s/bank?country=%s", baseUrl, country);
        Request request = new Request.Builder().url(URL).build();

        return newCall(request, new TypeReference<Response<List<Bank>>>() {});
    }

    private <T> T newCall(Request request, TypeReference<T> typeRef) {
        try {
            okhttp3.Response response = client.newCall(request).execute();

            String json = response.body().string();

            return JsonUtil.toValue(json, typeRef);
        } catch (IOException exception) {
            throw new PaystackException(exception.getMessage(), exception.getCause());
        }
    }
}
