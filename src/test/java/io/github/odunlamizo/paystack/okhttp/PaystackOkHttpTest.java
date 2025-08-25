package io.github.odunlamizo.paystack.okhttp;

import static org.junit.jupiter.api.Assertions.*;

import io.github.odunlamizo.paystack.PaystackException;
import io.github.odunlamizo.paystack.model.*;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

class PaystackOkHttpTest {

    private static MockWebServer mockWebServer;
    private PaystackOkHttp paystack;

    @BeforeAll
    static void startServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void stopServer() throws Exception {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void setUp() {
        String mockBaseUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        paystack = new PaystackOkHttp("test_secret", mockBaseUrl);
    }

    @Test
    void testResolveAccount() throws IOException {
        String json =
                """
                {
                    "status": true,
                    "message": "Account number resolved",
                    "data": {
                        "account_number": "0001234567",
                        "account_name": "Test User",
                        "bank_id": 3
                    }
                }
                """;

        mockWebServer.enqueue(
                new MockResponse().setBody(json).addHeader("Content-Type", "application/json"));

        Response<AccountDetails> response = paystack.resolveAccount("0001234567", "058");

        assertTrue(response.isStatus());
        assertEquals(200, response.getCode());
        assertEquals("Account number resolved", response.getMessage());
        assertEquals("0001234567", response.getData().getAccountNumber());
    }

    @Test
    void testListBanks() throws IOException {
        String json =
                """
                {
                    "status": true,
                    "message": "Banks retrieved",
                    "data": [
                        {
                            "name": "Access Bank",
                            "slug": "access-bank",
                            "code": "044",
                            "longcode": "044150149",
                            "gateway": "emandate",
                            "pay_with_bank": true,
                            "active": true,
                            "is_deleted": false,
                            "country": "Nigeria",
                            "currency": "NGN",
                            "type": "nuban",
                            "id": 1
                        }
                    ]
                }
                """;

        mockWebServer.enqueue(
                new MockResponse().setBody(json).addHeader("Content-Type", "application/json"));

        Response<List<Bank>> response = paystack.listBanks("nigeria");

        assertTrue(response.isStatus());
        assertEquals(200, response.getCode());
        assertEquals(1, response.getData().size());
        assertEquals("Access Bank", response.getData().get(0).getName());
        assertEquals("044", response.getData().get(0).getCode());
    }

    @Test
    void testInitializeTransactionSuccess() throws PaystackException, IOException {
        String json =
                """
                {
                  "status": true,
                  "message": "Authorization URL created",
                  "data": {
                    "authorization_url": "https://checkout.paystack.com/abc123",
                    "access_code": "xyz987",
                    "reference": "ref123"
                  }
                }
                """;

        mockWebServer.enqueue(
                new MockResponse().setBody(json).addHeader("Content-Type", "application/json"));

        InitializeTransactionRequest request =
                InitializeTransactionRequest.builder()
                        .amount("20000")
                        .email("test@example.com")
                        .currency("NGN")
                        .reference("ref123")
                        .build();

        Response<InitializeTransactionResponse> response = paystack.initializeTransaction(request);

        assertTrue(response.isStatus());
        assertEquals(200, response.getCode());
        assertEquals("Authorization URL created", response.getMessage());
        assertEquals(
                "https://checkout.paystack.com/abc123", response.getData().getAuthorizationUrl());
        assertEquals("xyz987", response.getData().getAccessCode());
        assertEquals("ref123", response.getData().getReference());
    }

    @Test
    void testInitializeTransactionFailure() throws PaystackException, IOException {
        String json =
                """
                {
                  "status": false,
                  "message": "Invalid email supplied",
                  "data": null
                }
                """;

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setBody(json)
                        .addHeader("Content-Type", "application/json"));

        InitializeTransactionRequest request =
                InitializeTransactionRequest.builder()
                        .amount("20000")
                        .email("bad-email")
                        .currency("NGN")
                        .reference("ref123")
                        .build();

        Response<InitializeTransactionResponse> response = paystack.initializeTransaction(request);

        assertFalse(response.isStatus());
        assertEquals(400, response.getCode());
        assertEquals("Invalid email supplied", response.getMessage());
        assertNull(response.getData());
    }
}
