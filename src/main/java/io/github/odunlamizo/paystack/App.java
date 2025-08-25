package io.github.odunlamizo.paystack;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.odunlamizo.paystack.model.InitializeTransactionRequest;
import io.github.odunlamizo.paystack.okhttp.PaystackOkHttp;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().load();

        // Get the API key from .env
        String apiKey = dotenv.get("PAYSTACK_API_KEY");

        // Check if the API key exists
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Error: PAYSTACK_API_KEY not found in .env file");
            System.exit(1);
        }

        Paystack paystack = new PaystackOkHttp(apiKey);
        // System.out.println(paystack.resolveAccount("9036678078", "999992"));
        // System.out.println(paystack.listBanks("nigeria"));
        try {
            System.out.println(
                    paystack.initializeTransaction(
                            InitializeTransactionRequest.builder()
                                    .email("tunde@gmail.com")
                                    .amount("1000")
                                    .build()));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
