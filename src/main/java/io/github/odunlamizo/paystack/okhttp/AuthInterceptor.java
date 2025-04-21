package io.github.odunlamizo.paystack.okhttp;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {
    private final String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request requestWithAuth =
                original.newBuilder().header("Authorization", "Bearer " + token).build();
        return chain.proceed(requestWithAuth);
    }
}
