package io.github.odunlamizo.paystack.okhttp;

import static org.junit.jupiter.api.Assertions.*;

import io.github.odunlamizo.paystack.model.AccountDetails;
import io.github.odunlamizo.paystack.model.Bank;
import io.github.odunlamizo.paystack.model.Response;
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
    void testResolveAccount() {
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
    void testListBanks() {
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
}
