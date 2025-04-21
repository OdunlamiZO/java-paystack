package io.github.odunlamizo.paystack;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.odunlamizo.paystack.okhttp.PaystackOkHttp;

public class App {

    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().load();

        // Get API key from .env
        String apiKey = dotenv.get("PAYSTACK_API_KEY");

        // Check if the API key exists
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Error: PAYSTACK_API_KEY not found in .env file");
            System.exit(1);
        }

        Paystack paystack = new PaystackOkHttp(apiKey);
        System.out.println(paystack.resolveAccount("9036678078", "999992"));
        // System.out.println(paystack.listBanks("nigeria"));
    }
}
